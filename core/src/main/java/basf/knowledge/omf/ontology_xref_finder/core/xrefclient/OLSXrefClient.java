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

import basf.knowledge.omf.ontology_xref_finder.core.pojo.OLSSearch;
import basf.knowledge.omf.ontology_xref_finder.core.pojo.OLSSearchItem;
import basf.knowledge.omf.ontology_xref_finder.core.utils.APIQueryParams;

// TODO Refactor this in a new package with some interface
class OLSEndpoint {
	private static final String SEARCH_CTXT = "/search";
	private static final String PARAM_QUERY_FIELDS = "queryFields";
	private static final String PARAM_ROWS = "rows";
	private static final String PARAM_ONTOLOGY = "ontology";
	private static final String ENABLE_EXACT_MATCH = "exact=on";

	/**
	 * Creates an APIQueryParams object to generate an API call that searches
	 * through all the ontologies in the OLS.
	 * @param query Query parameter (e.g: Destillation)
	 * @param queryFields Fields to consider in the search (e.g: label,synonym)
	 * @param rows Maximum number of results (e.g: 2)
	 * @return An APIQueryParams wrapping the request parameters.
	 */
	public static APIQueryParams search(String query, String queryFields, Integer rows) {
		return search(query, queryFields, rows, null);
	}
	
	/**
	 * Creates an APIQueryParams object to generate an API call that searches
	 * through a specific set of ontologies in the OLS.
	 * @param query Query parameter (e.g: Destillation)
	 * @param queryFields Fields to consider in the search (e.g: label,synonym)
	 * @param rows Maximum number of results (e.g: 2)
	 * @param ontologiesFilter List of ontologies to consider in the search (e.g: doid,clo)
	 * @return An APIQueryParams wrapping the request parameters.
	 */
	public static APIQueryParams search(String query, String queryFields, Integer rows, List<String> ontologiesFilter) {
		APIQueryParams apiQueryparams = new APIQueryParams();
		apiQueryparams.setContextPath(SEARCH_CTXT);
		apiQueryparams.addQueryParam("q", query); // q=Destillation
		if (queryFields != null && !query.isEmpty()) {
			apiQueryparams.addQueryParam(PARAM_QUERY_FIELDS, queryFields); // queryFields=label,synonym
		}
		if (rows != null && rows > 0) {
			apiQueryparams.addQueryParam(PARAM_ROWS, rows); // rows=2
		}
		
		if (ontologiesFilter != null && !ontologiesFilter.isEmpty()) {
			apiQueryparams.addQueryParam(PARAM_ONTOLOGY, String.join(",", ontologiesFilter)); // ontology=doid,clo
		}

		return apiQueryparams;
	}
}

public class OLSXrefClient extends AbstractXrefClient {
	private static final Logger LOGGER = Logger.getLogger(OLSXrefClient.class.getName());
	private static final String QUERY_FIELDS = "label,synonym";

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

	@Override
	protected List<IRI> search(OWLAnnotation annotation) throws SocketException {
		OWLLiteral literal = annotation.getValue().asLiteral().get();
		String literalValue = literal.getLiteral();
		WebTarget client = createClient(OLSEndpoint.search(literalValue,
				QUERY_FIELDS, this.max_xrefs, this.ontologiesFilter));
		Response response = client.request(MediaType.APPLICATION_JSON).get();
		OLSSearch olsSearch = null;
        //System.out.println(response.readEntity(String.class));
        if (Status.OK.getStatusCode() != response.getStatus()) {
        	String error = "Could not process request, received status '" + response.getStatus() + "'";
        	LOGGER.warning(error);
        	throw new SocketException(error);
        }
        olsSearch = response.readEntity(OLSSearch.class);
        List<OLSSearchItem> items = olsSearch.getResponse().getDocs();
        if (items.isEmpty()) {
        	LOGGER.warning("Could not find XRefs for annotation '" + literalValue + "'");
        	return  new LinkedList<IRI>();
        } else {
        	LOGGER.info("Found '" + items.size() + "' possible XRefs for '" + literalValue + "'");
        }
        List<IRI> result = new LinkedList<IRI>();
        for (OLSSearchItem olsSearchItem : items) {
        	result.add(IRI.create(olsSearchItem.getIri()));
		}
		return result;
	}

}
