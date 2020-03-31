package basf.knowledge.omf.ontology_xref_finder.core.xrefclient;

import java.io.File;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.management.RuntimeErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.search.EntitySearcher;

import basf.knowledge.omf.ontology_xref_finder.core.interfaces.IXrefClient;
import basf.knowledge.omf.ontology_xref_finder.core.utils.APIQueryParams;
import basf.knowledge.omf.ontology_xref_finder.core.utils.Constants;
import basf.knowledge.omf.ontology_xref_finder.core.utils.QueryParam;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

public abstract class AbstractXrefClient implements IXrefClient {
	private static final Logger LOGGER = Logger.getLogger(AbstractXrefClient.class.getName());
	public static final Integer INFINITE_XREFS = -1; // Search all the Xrefs available in the API
	protected String url;
	protected OWLOntology ontology;
	protected Integer max_xrefs; // Maximum number of xrefs to add per term
	protected List<String> ontologiesFilter = null; // Ontologies considered in a search
	protected final OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	protected final OWLDataFactory factory = new OWLDataFactoryImpl();

	public AbstractXrefClient(String url, File ontologyFile, Integer max_xrefs) throws OWLOntologyCreationException {
		this.url = url;
		this.ontology = manager.loadOntologyFromOntologyDocument(ontologyFile);
		this.max_xrefs = max_xrefs;
		LOGGER.info("Using API: " + url);
		LOGGER.info("Ontology file: " + ontologyFile.getAbsolutePath());
		LOGGER.info("Max. xrefs per term: " + max_xrefs);
	}

	public AbstractXrefClient(String url, String ontologyFilePath, Integer max_xrefs) throws OWLOntologyCreationException {
		this(url, new File(ontologyFilePath), max_xrefs);
	}
	
	public AbstractXrefClient(String url, String ontologyFilePath) throws OWLOntologyCreationException {
		this(url, new File(ontologyFilePath), INFINITE_XREFS);
	}
	
	public AbstractXrefClient(String url, File ontologyFile) throws OWLOntologyCreationException {
		this(url, ontologyFile, INFINITE_XREFS);
	}

	protected WebTarget createClient(APIQueryParams apiQueryParams) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url.concat(apiQueryParams.getContextPath()));
		for (QueryParam qp : apiQueryParams.getQueryParams()) {
			target = target.queryParam(qp.getQuery(), qp.getParam());
		}
		if (Constants.DEV_MODE) {
			LOGGER.info(String.format("Calling endpoint %s", target.getUri()));
		}
		return target;
	}
	
	private String assembleEndpoint(String path) {
        String endpoint = url.concat(path);
        //URLEncoder.encode(path, "UTF-8")
        return endpoint;
    }

	public OWLOntology getOntology() {
		return ontology;
	}

	public OWLOntologyManager getManager() {
		return manager;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getMax_xrefs() {
		return max_xrefs;
	}

	public void setMax_xrefs(Integer max_xrefs) {
		this.max_xrefs = max_xrefs;
	}

	/**
	 * Returns a list of IRIs that are Xref to owlClass
	 * according to its rdfs:label (if present).
	 */
	public Stream<IRI> findXrefByLabel(OWLClass owlClass)  throws SocketException {
		return findXrefByIRI(owlClass, Constants.RDF_LABEL);
	}

	public Stream<IRI> findXrefByIRI(OWLClass owlClass, IRI wantedIRI)  throws SocketException {
		List<IRI> xrefIRIs = new LinkedList<IRI>();
		
		EntitySearcher.getAnnotations(owlClass, ontology).forEach(annotation -> {
			if (annotation.getProperty().getIRI().equals(wantedIRI)) {
				try {
					xrefIRIs.addAll(searchXref(annotation));
				} catch (SocketException e) {
					throw new RuntimeException(e.getMessage());
				}
				
			}
		});
		
		// Delete IRI if the xref is the own IRI of of owlClass.
		xrefIRIs.removeIf(iri -> {
			if (iri.equals(owlClass.getIRI())) {
				return true;
			}
			return false;
		});
		return xrefIRIs.stream();
	}
	
	public long getNumberOfClasses() {
		return this.ontology.classesInSignature().count();
	}
	
	/**
	 * This method loops through the OWL classes and tries to find
	 * xrefs given a OWL class rdfs:label. The annotations are added
	 * to this.ontology.
	 */
	public void processOntologyXrefs() {
		LOGGER.info("Detected '" + getNumberOfClasses() + "' classes in input ontology.");
		this.ontology.classesInSignature()
				.forEach(owlClass -> {
					List<IRI> xrefList;
					try {
						Stream<IRI> xrefs = findXrefByLabel(owlClass);
						xrefList = xrefs.collect(Collectors.toList());
					} catch (SocketException e) {
						// Wrapper, streams cannot throw checked exceptions...
						// See: https://stackoverflow.com/questions/23548589/java-8-how-do-i-work-with-exception-throwing-methods-in-streams
						throw new RuntimeException(e.getMessage());
					}
					addXrefToClass(owlClass, xrefList);
				});
	}
	
	public void addXrefToClass(OWLClass owlClass, List<IRI> xrefList) {
		OWLAnnotationProperty annotationProperty = factory.getOWLAnnotationProperty(Constants.GO_HAS_DX_XREF);
		List<OWLAxiom> axioms = new LinkedList<OWLAxiom>();
		for (IRI iri : xrefList) {
			axioms.add(createAnnotationForClass(owlClass, annotationProperty, iri.getIRIString()));
		}
		manager.addAxioms(this.ontology, axioms.stream());
	}
	
	protected OWLAnnotationAssertionAxiom createAnnotationForClass(OWLClass owlClass, OWLAnnotationProperty annotationProperty, String annotationValue) {
		OWLAnnotation annotation = factory.getOWLAnnotation(annotationProperty, factory.getOWLLiteral(annotationValue));
		OWLAnnotationAssertionAxiom  annotationAxiom = factory.getOWLAnnotationAssertionAxiom(owlClass.getIRI(), annotation);
		return annotationAxiom;
	}

	protected abstract List<IRI> searchXref(OWLAnnotation annotation) throws SocketException;
	
	public List<String> getOntologiesFilter() {
		return ontologiesFilter;
	}

	public void setOntologiesFilter(List<String> ontologiesFilter) {
		this.ontologiesFilter = ontologiesFilter;
	}
	

}
