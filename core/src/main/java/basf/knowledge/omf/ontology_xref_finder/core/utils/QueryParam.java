package basf.knowledge.omf.ontology_xref_finder.core.utils;

public class QueryParam {
	private String query;
	private Object param;

	public QueryParam() {

	}

	public QueryParam(String query, Object param) {
		this.query = query;
		this.param = param;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}
}
