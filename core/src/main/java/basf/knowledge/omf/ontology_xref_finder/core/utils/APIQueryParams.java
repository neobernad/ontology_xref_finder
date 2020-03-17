package basf.knowledge.omf.ontology_xref_finder.core.utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Wrapper to store information of an API query to a certain contextPath.
 * For instance:
 * 	contextPath = /search
 * 	queryParams = List({query="q", param="methanol"}, {query="rows", param="2"})
 */
public class APIQueryParams {
	private String contextPath;
	private List<QueryParam> queryParams;
	
	public APIQueryParams() {
		queryParams = new LinkedList<QueryParam>();
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public List<QueryParam> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(List<QueryParam> queryParams) {
		this.queryParams = queryParams;
	}
	
	public void addQueryParam(String query, Object param) {
		this.queryParams.add(new QueryParam(query, param));
	}
	
	public void addQueryParam(QueryParam queryParam) {
		this.queryParams.add(queryParam);
	}
	
	public void addQueryParams(List<QueryParam> queryParams) {
		this.queryParams.addAll(queryParams);
	}
	
	
}

