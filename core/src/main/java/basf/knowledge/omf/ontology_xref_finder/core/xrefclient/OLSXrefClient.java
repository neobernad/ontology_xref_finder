package basf.knowledge.omf.ontology_xref_finder.core.xrefclient;

import java.io.File;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import basf.knowledge.omf.ontology_xref_finder.core.endpoints.OLSEndpoint;
import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyMetadata;
import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyTerm;
import basf.knowledge.omf.ontology_xref_finder.core.pojo.OLSOntologiesEmbedded;
import basf.knowledge.omf.ontology_xref_finder.core.pojo.OLSSearch;
import basf.knowledge.omf.ontology_xref_finder.core.pojo.OLSSearchItem;
import basf.knowledge.omf.ontology_xref_finder.core.pojo.OLSTermsEmbedded;
import basf.knowledge.omf.ontology_xref_finder.core.service.XrefProcessXLSXReporter;
import basf.knowledge.omf.ontology_xref_finder.core.utils.PojoMapper;

public class OLSXrefClient extends AbstractXrefClient {
	private static final Logger LOGGER = Logger.getLogger(OLSXrefClient.class.getName());
	private static final String QUERY_FIELDS = "label,synonym";
	private static final Integer MAX_PAGE_SIZE = 500;
	
	private static PojoMapper pojoMapper = PojoMapper.INSTANCE;

	public OLSXrefClient(String url, File ontology, Integer max_xrefs) throws OWLOntologyCreationException {
		super(url, ontology, max_xrefs);
	}

	public OLSXrefClient(String url, String ontology, Integer max_xrefs) throws OWLOntologyCreationException {
		super(url, ontology, max_xrefs);
	}
	
	public OLSXrefClient(String url, File ontology) throws OWLOntologyCreationException {
		super(url, ontology);
	}
	
	public OLSXrefClient(String url, String ontology) throws OWLOntologyCreationException {
		super(url, ontology);
	}
	
	public OLSXrefClient(String url, Integer max_xrefs) {
		super(url, max_xrefs);
	}

	@Override
	protected List<IRI> searchXref(OWLAnnotation annotation) throws SocketException {
		OWLLiteral literal = annotation.getValue().asLiteral().get();
		String literalValue = literal.getLiteral();
		return searchXref(literalValue);
	}
	
	@Override
	protected List<IRI> searchXref(String text) throws SocketException {
		WebTarget client = createClient(OLSEndpoint.search(text,
				QUERY_FIELDS, this.max_xrefs, this.exactMatch, this.ontologiesFilter));
		Response response = client.request(MediaType.APPLICATION_JSON).get();
        // System.out.println(response.readEntity(String.class));
        if (Status.OK.getStatusCode() != response.getStatus()) {
        	String error = "Could not process request, received status '" + response.getStatus() + "'";
        	LOGGER.warning(error);
        	throw new SocketException(error);
        }
        OLSSearch olsSearch = response.readEntity(OLSSearch.class);
        List<OLSSearchItem> items = olsSearch.getResponse().getDocs();
        if (items.isEmpty()) {
        	LOGGER.warning("Could not find XRefs for annotation '" + text + "'");
        	return  new LinkedList<IRI>();
        } 
        List<IRI> result = new LinkedList<IRI>();
        for (OLSSearchItem olsSearchItem : items) {
        	if (olsSearchItem.isClass()) { // We could find properties, but those are not Xrefs
        		result.add(IRI.create(olsSearchItem.getIri()));
        	}
		}
        if (!result.isEmpty()) {
        	LOGGER.info("Found '" + result.size() + "' crossreference/s for '" + text + "'");
        }
		return result;
	}

	@Override
	public List<OntologyMetadata> getAllAvailableOntologies() throws SocketException {
		return getAvailableOntologies(0, MAX_PAGE_SIZE);
	}
	
	@Override
	public List<OntologyMetadata> getAvailableOntologies(Integer page, Integer size) throws SocketException {
		WebTarget client = createClient(OLSEndpoint.getOntologies(page, size));
		Response response = client.request(MediaType.APPLICATION_JSON).get();
		// System.out.println(response.readEntity(String.class));
		if (Status.OK.getStatusCode() != response.getStatus()) {
        	String error = "Could not process request, received status '" + response.getStatus() + "'";
        	LOGGER.warning(error);
        	throw new SocketException(error);
        }
		OLSOntologiesEmbedded olsOntologiesEmbedded = response.readEntity(OLSOntologiesEmbedded.class);
		List<OntologyMetadata> result = pojoMapper.map(olsOntologiesEmbedded);
		return result;
		
	}
	/**
	 * Retrieves a term from the OLS API depending on the @param iri
	 * 
	 * @param iri IRI of the term to retrieve.
	 * @return A list of terms with only 1 term. A list is returned
	 * due to the API encapsulates the result in a JSON array, though only 
	 * 1 item (term) is returned.
	 */
	@Override
	public List<OntologyTerm> getTerm(IRI iri) throws SocketException {
		WebTarget client = createClient(OLSEndpoint.getTerm(iri));
		Response response = client.request(MediaType.APPLICATION_JSON).get();
//		 System.out.println(response.readEntity(String.class));
		if (Status.OK.getStatusCode() != response.getStatus()) {
			String error = "Could not process request, received status '" + response.getStatus() + "'";
			LOGGER.warning(error);
			throw new SocketException(error);
		}
		OLSTermsEmbedded olsTermEmbedded = response.readEntity(OLSTermsEmbedded.class);
		List<OntologyTerm> result = pojoMapper.map(olsTermEmbedded);
		return result;
	}

}
