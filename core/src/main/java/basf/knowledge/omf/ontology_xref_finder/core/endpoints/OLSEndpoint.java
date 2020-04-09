package basf.knowledge.omf.ontology_xref_finder.core.endpoints;

import java.util.List;

import org.semanticweb.owlapi.model.IRI;

import basf.knowledge.omf.ontology_xref_finder.core.utils.APIQueryParams;

//TODO Refactor this in a new package with some interface
public class OLSEndpoint {
	private static final String SEARCH_CTXT = "/search";
	private static final String ONTOLOGY_CTXT = "/ontologies";
	private static final String TERMS_CTXT = "/terms";
	
	private static final String PARAM_PAGE = "page";
	private static final String PARAM_IRI = "iri";
	private static final String PARAM_SIZE = "size";
	private static final String PARAM_QUERY_FIELDS = "queryFields";
	private static final String PARAM_ROWS = "rows";
	private static final String PARAM_ONTOLOGY = "ontology";
	private static final String PARAM_EXACT_MATCH = "exact";
	private static final String VAL_EXACT_MATCH = "on";
	
	
	public static APIQueryParams getTerm(IRI iri) {
		APIQueryParams apiQueryparams = new APIQueryParams();
		apiQueryparams.setContextPath(TERMS_CTXT);
		
		if (iri != null) {
			apiQueryparams.addQueryParam(PARAM_IRI, iri.toString()); // iri=http://purl.obolibrary.org/obo/NCIT_C2985 
		}
		
		return apiQueryparams;
	}
	
	public static APIQueryParams getOntologies(Integer page, Integer size) {
		APIQueryParams apiQueryparams = new APIQueryParams();
		apiQueryparams.setContextPath(ONTOLOGY_CTXT);
		
		if (page != null && page > 0) {
			apiQueryparams.addQueryParam(PARAM_PAGE, page); // page=0
		}
		
		if (size != null && size > 0) {
			apiQueryparams.addQueryParam(PARAM_SIZE, size); // size=2
		}
		
		return apiQueryparams;
	}

	/**
	 * Creates an APIQueryParams object to generate an API call that searches
	 * through all the ontologies in the OLS.
	 * @param query Query parameter (e.g: Destillation)
	 * @param queryFields Fields to consider in the search (e.g: label,synonym)
	 * @param rows Maximum number of results (e.g: 2)
	 * @return An APIQueryParams wrapping the request parameters.
	 */
	public static APIQueryParams search(String query, String queryFields, Integer rows, Boolean exactSearch) {
		return search(query, queryFields, rows, exactSearch, null);
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
	public static APIQueryParams search(String query, String queryFields, Integer rows, 
			Boolean exactSearch, List<String> ontologiesFilter) {
		APIQueryParams apiQueryparams = new APIQueryParams();
		apiQueryparams.setContextPath(SEARCH_CTXT);
		apiQueryparams.addQueryParam("q", query); // q=Destillation
		if (queryFields != null && !query.isEmpty()) {
			apiQueryparams.addQueryParam(PARAM_QUERY_FIELDS, queryFields); // queryFields=label,synonym
		}
		if (rows != null && rows > 0) {
			apiQueryparams.addQueryParam(PARAM_ROWS, rows); // rows=2
		}
		if (exactSearch != null && exactSearch.booleanValue()) {
			apiQueryparams.addQueryParam(PARAM_EXACT_MATCH, VAL_EXACT_MATCH); // exact=on
		}
		if (ontologiesFilter != null && !ontologiesFilter.isEmpty()) {
			apiQueryparams.addQueryParam(PARAM_ONTOLOGY, String.join(",", ontologiesFilter)); // ontology=doid,clo
		}
		return apiQueryparams;
	}
}
