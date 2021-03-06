package basf.knowledge.omf.ontology_xref_finder.core.xrefclient;

import java.io.File;
import java.net.SocketException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import basf.knowledge.omf.ontology_xref_finder.core.interfaces.IXrefProcessReporter;
import basf.knowledge.omf.ontology_xref_finder.core.model.OntologySynonym;
import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyTerm;
import basf.knowledge.omf.ontology_xref_finder.core.model.XrefMatch;
import basf.knowledge.omf.ontology_xref_finder.core.service.XrefProcessPlainReporter;
import basf.knowledge.omf.ontology_xref_finder.core.service.XrefProcessXLSXReporter;
import basf.knowledge.omf.ontology_xref_finder.core.utils.APIQueryParams;
import basf.knowledge.omf.ontology_xref_finder.core.utils.Constants;
import basf.knowledge.omf.ontology_xref_finder.core.utils.QueryParam;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

public abstract class AbstractXrefClient implements IXrefClient {
	private static Logger LOGGER = Logger.getLogger(AbstractXrefClient.class.getName());
	protected final OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	protected final OWLDataFactory factory = new OWLDataFactoryImpl();
	public static final Integer INFINITE_XREFS = -1; // Search all the Xrefs available in the API
	protected IXrefProcessReporter xrefProcessReporter = null;
	protected String url;
	protected File ontologyFile;
	protected OWLOntology ontology;
	protected Integer max_xrefs; // Maximum number of xrefs to add per term
	protected List<String> ontologiesFilter = null; // Ontologies considered in a search
	protected Boolean noDbXref = false;
	protected Boolean exactMatch = false;


	public AbstractXrefClient(String url, File ontologyFile, Integer max_xrefs) throws OWLOntologyCreationException {
		this.url = url;
		this.ontologyFile = ontologyFile;
		this.ontology = manager.loadOntologyFromOntologyDocument(ontologyFile);
		this.max_xrefs = max_xrefs;
		this.xrefProcessReporter = new XrefProcessXLSXReporter(this.ontologyFile.getParent());
	}
	
	public AbstractXrefClient(String url, Integer max_xrefs) {
		this.url = url;
		this.ontology = null;
		this.max_xrefs = max_xrefs;
		this.xrefProcessReporter = new XrefProcessPlainReporter(null);
	}

	public AbstractXrefClient(String url, String ontologyFilePath, Integer max_xrefs)
			throws OWLOntologyCreationException {
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
		if (Constants.DEBUG_MODE) {
			LOGGER.info(String.format("Calling endpoint %s", target.getUri()));
		}
		return target;
	}

	private String assembleEndpoint(String path) {
		String endpoint = url.concat(path);
		// URLEncoder.encode(path, "UTF-8")
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
	
	public Boolean getExactMatch() {
		return exactMatch;
	}

	public void setExactMatch(Boolean exactMatch) {
		this.exactMatch = exactMatch;
	}

	/**
	 * Returns a list of IRIs that are Xref to owlClass according to its rdfs:label
	 * (if present).
	 */
	public Stream<XrefMatch> findXrefByLabel(OWLClass owlClass) throws SocketException {
		return findXrefByIRI(owlClass, Constants.RDFS_LABEL);
	}
	
	public Stream<XrefMatch> findXrefByText(List<String> termList) throws SocketException {
		List<XrefMatch> matchedXrefs = new LinkedList<XrefMatch>();
		for (String term : termList) {
			List<IRI> retrievedIRIs = searchXref(term);
			if (retrievedIRIs.isEmpty()) {
				xrefProcessReporter.addNoXrefFound(null, term);
			}
			matchedXrefs.add(new XrefMatch(term, retrievedIRIs));
		}
		return matchedXrefs.stream();
	}
	
	public Stream<XrefMatch> findXrefByIRI(OWLClass owlClass, IRI annotationIRI) throws SocketException {
		List<XrefMatch> matchedXrefs = new LinkedList<XrefMatch>();
		
		OWLAnnotation owlAnnotation = EntitySearcher.getAnnotations(owlClass, ontology)
				.filter(annotation -> annotation.getProperty().getIRI().equals(annotationIRI))
				.findFirst().orElse(null);
		
		if (owlAnnotation != null) {
			try {
				List<IRI> retrievedIRIs = searchXref(owlAnnotation);
				String annotationLiteral = owlAnnotation.literalValue().get().getLiteral();
				if (retrievedIRIs.isEmpty()) {
					xrefProcessReporter.addNoXrefFound(owlClass.getIRI(), annotationLiteral);
				}
				matchedXrefs.add(new XrefMatch(annotationLiteral, retrievedIRIs));
			} catch (SocketException e) {
				throw new RuntimeException(e.getMessage());
			}

			// Delete IRI if the xref is the own IRI of of owlClass.
			matchedXrefs.removeIf(iri -> {
				if (iri.equals(owlClass.getIRI())) {
					return true;
				}
				return false;
			});
		}
		
		
		return matchedXrefs.stream();
	}

	public long getNumberOfClasses() {
		return this.ontology.classesInSignature().count();
	}
	
	private String getOWLClassDefinition(OWLClass owlClass) {
		OWLAnnotation classDefinition = EntitySearcher.getAnnotations(owlClass, ontology)
				.filter(annotation -> annotation.getProperty().getIRI().equals(Constants.OBO_DEF))
				.findFirst().orElse(null);
		if (classDefinition == null) {
			classDefinition = EntitySearcher.getAnnotations(owlClass, ontology)
					.filter(annotation -> annotation.getProperty().getIRI().equals(Constants.RDFS_COMMENT))
					.findFirst().orElse(null);
		}
		
		if (classDefinition == null) {
			return "";
		}
		return classDefinition.literalValue().get().getLiteral();
	}

	/**
	 * This method loops through the OWL classes and tries to find xrefs given a OWL
	 * class rdfs:label. The annotations are added to this.ontology.
	 */
	public void processOntologyXrefs() {
		if (this.ontology == null) {
			throw new NullPointerException("No ontology defined");
		}
		LOGGER.info("Configuration is: " + toString());
		LOGGER.info("Detected '" + getNumberOfClasses() + "' classes in input ontology.");
		this.ontology.classesInSignature().forEach(owlClass -> {
			try {
				Stream<XrefMatch> xrefStream = findXrefByLabel(owlClass);
				List<XrefMatch> xrefList = xrefStream.collect(Collectors.toList());
				String owlClassDefinition = getOWLClassDefinition(owlClass);
				// xrefClient.addXrefToClass(owlClass, xrefList);
				for (XrefMatch xrefMatch : xrefList) {
					String classLiteral = xrefMatch.getLiteral();
					for (IRI xrefIri : xrefMatch.getMatchedIRIs()) {
						List<OntologyTerm> ontologyTerms = getTerm(xrefIri);
						if (ontologyTerms.isEmpty()) {
							xrefProcessReporter.addClassesWithoutXrefData(owlClass.getIRI(), xrefIri.getIRIString());
						} else {
							xrefProcessReporter.addXrefFound(classLiteral, owlClassDefinition, xrefIri, ontologyTerms);
						}
						addSynonymsToClass(owlClass, ontologyTerms, xrefIri);
					}
				}
				
			} catch (SocketException e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * This method loops through the a stream of XrefMatches to find the xrefs.
	 * 
	 */
	public IXrefProcessReporter processXrefs(Stream<XrefMatch> xrefStream) {
//		System.out.println(toString());
		try {
			List<XrefMatch> xrefList = xrefStream.collect(Collectors.toList());
			for (XrefMatch xrefMatch : xrefList) {
				String classLiteral = xrefMatch.getLiteral();
				for (IRI xrefIri : xrefMatch.getMatchedIRIs()) {
					List<OntologyTerm> ontologyTerms = getTerm(xrefIri);
					if (ontologyTerms.isEmpty()) {
						xrefProcessReporter.addClassesWithoutXrefData(null, xrefIri.getIRIString());
					} else {
						xrefProcessReporter.addXrefFound(classLiteral, null, xrefIri, ontologyTerms);
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return getXrefProcessReporter();
	}

	public void addXrefToClass(OWLClass owlClass, List<IRI> xrefList) {
		OWLAnnotationProperty annotationProperty = factory.getOWLAnnotationProperty(Constants.GO_HAS_DB_XREF);
		List<OWLAxiom> axioms = new LinkedList<OWLAxiom>();
		for (IRI iri : xrefList) {
			axioms.add(createAnnotationForClass(owlClass, annotationProperty, iri.getIRIString()));
		}
		manager.addAxioms(this.ontology, axioms.stream());
	}
	
	private OWLAnnotation createOwlAnnotationDBXref(IRI xrefIri) {
		OWLAnnotationProperty dbxref = factory.getOWLAnnotationProperty(Constants.GO_HAS_DB_XREF);
		OWLAnnotation xrefAnnotation = factory.getOWLAnnotation(dbxref,
				factory.getOWLLiteral(xrefIri.getIRIString()));
		return xrefAnnotation;
		
	}

	public void addSynonymsToClass(OWLClass owlClass, List<OntologyTerm> ontologyTerms, IRI xref) {
		for (OntologyTerm ontologyTerm : ontologyTerms) {
			List<OWLAxiom> axioms = new LinkedList<OWLAxiom>();
			OWLAnnotation xrefAnnotation = null;
			if (!this.noDbXref) { // Add xref IRI to the annotation?
				xrefAnnotation = createOwlAnnotationDBXref(xref);
			}
			if (ontologyTerm.getObo_synonym().isEmpty()) {
				xrefProcessReporter.addClassesWithoutXrefSynonyms(owlClass.getIRI(), xref.getIRIString());
			} else {
				// Add term synonyms as synonyms of owlClass
				for (OntologySynonym synonym : ontologyTerm.getObo_synonym()) {
					IRI scopeAnnotationProperty = null;
					// TODO: Refactor this. Some OWLUtils class that returns the correct IRI
					if (synonym.getScope().equals(Constants.HAS_EXACT_SYN)) {
						scopeAnnotationProperty = Constants.GO_HAS_EXACT_SYN;
					} else if (synonym.getScope().equals(Constants.HAS_NARROW_SYN)) {
						scopeAnnotationProperty = Constants.GO_HAS_NARROW_SYN;
					} else if (synonym.getScope().equals(Constants.HAS_RELATED_SYN)) {
						scopeAnnotationProperty = Constants.GO_HAS_RELATED_SYN;
					} else {
						scopeAnnotationProperty = Constants.GO_HAS_SYN;
					}
					OWLAnnotationProperty scope = factory.getOWLAnnotationProperty(scopeAnnotationProperty);
					OWLAnnotation synonymAnnotation = factory.getOWLAnnotation(scope,
							factory.getOWLLiteral(synonym.getName()));
					
					if (!existsAnnotationOnClass(owlClass, synonymAnnotation)) {
						OWLAnnotationAssertionAxiom annotationAxiom = factory.getOWLAnnotationAssertionAxiom(owlClass.getIRI(),
								synonymAnnotation, Collections.singletonList(xrefAnnotation));
						axioms.add(annotationAxiom);
						if (Constants.DEBUG_MODE) {
							LOGGER.info("Adding synonym axiom '" + annotationAxiom + "'");
						}
					} else {
						if (Constants.DEBUG_MODE) {
							LOGGER.warning("Skipping a synonym already present '" + synonymAnnotation + "'");
						}
					}
				}
			}
			// Add term label as a synonym (generic) of owlClass
			OWLAnnotation termLabelAsAnnotation = factory.getOWLAnnotation(
					factory.getOWLAnnotationProperty(Constants.GO_HAS_SYN),
					factory.getOWLLiteral(ontologyTerm.getLabel()));
			if (!existsAnnotationOnClass(owlClass, termLabelAsAnnotation)) {
				OWLAnnotationAssertionAxiom annotationAxiom = factory.getOWLAnnotationAssertionAxiom(owlClass.getIRI(),
						termLabelAsAnnotation, Collections.singletonList(xrefAnnotation));
				axioms.add(annotationAxiom);
			}
			manager.addAxioms(this.ontology, axioms.stream());
		}

	}
	
	private boolean existsAnnotationOnClass(OWLClass owlClass, OWLAnnotation owlAnnotation) {
		Optional<OWLAnnotation> existingAnnotation = EntitySearcher.getAnnotations(owlClass, ontology)
														.filter(annotation -> annotation.literalValue().get().getLiteral()
																.equals(owlAnnotation.literalValue().get().getLiteral()))
														.findAny();
		return existingAnnotation.isPresent();
	}

	protected OWLAnnotationAssertionAxiom createAnnotationForClass(OWLClass owlClass,
			OWLAnnotationProperty annotationProperty, String annotationValue) {
		OWLAnnotation annotation = factory.getOWLAnnotation(annotationProperty, factory.getOWLLiteral(annotationValue));
		OWLAnnotationAssertionAxiom annotationAxiom = factory.getOWLAnnotationAssertionAxiom(owlClass.getIRI(),
				annotation);
		return annotationAxiom;
	}

	protected abstract List<IRI> searchXref(OWLAnnotation annotation) throws SocketException;
	protected abstract List<IRI> searchXref(String text) throws SocketException;

	public List<String> getOntologiesFilter() {
		return ontologiesFilter;
	}

	public void setOntologiesFilter(List<String> ontologiesFilter) {
		this.ontologiesFilter = ontologiesFilter;
	}

	public Boolean getNoDbXref() {
		return noDbXref;
	}

	public void setNoDbXref(Boolean noDbXref) {
		this.noDbXref = noDbXref;
	}
	

	public IXrefProcessReporter getXrefProcessReporter() {
		return xrefProcessReporter;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractXrefClient [url=");
		builder.append(url);
		if (ontology != null) {
			builder.append(", ontology=");
			builder.append(ontology.getOntologyID().getOntologyIRI());
		}
		builder.append(", max_xrefs=");
		builder.append(max_xrefs);
		builder.append(", ontologiesFilter=");
		builder.append(ontologiesFilter);
		builder.append(", noDbXref=");
		builder.append(noDbXref);
		builder.append(", exactMatch=");
		builder.append(exactMatch);
		builder.append("]");
		return builder.toString();
	}
	
	

}
