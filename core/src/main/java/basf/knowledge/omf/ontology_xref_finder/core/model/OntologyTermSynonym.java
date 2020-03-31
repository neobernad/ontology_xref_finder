package basf.knowledge.omf.ontology_xref_finder.core.model;

import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

import basf.knowledge.omf.ontology_xref_finder.core.pojo.OLSTermsItemSynonym;

public class OntologyTermSynonym {
	
	public static class Builder {
		private String name;
		private String scope;
		private String type;
		private List<String> xrefs;

		public Builder() {

		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withScope(String scope) {
			this.scope = scope;
			return this;
		}


		public Builder withType(String type) {
			this.type = type;
			return this;
		}
		
		public Builder withXrefs(List<String> xrefs) {
			this.xrefs = xrefs;
			return this;
		}


		public OntologyTermSynonym build() {
			OntologyTermSynonym synonym = new OntologyTermSynonym();
			synonym.name = this.name;
			synonym.scope = this.scope;
			synonym.type = this.type;
			synonym.xrefs = this.xrefs;
			return synonym;
		}

	}

	private String name;
	private String scope;
	private String type;
	private List<String> xrefs;

	private OntologyTermSynonym() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getXrefs() {
		return xrefs;
	}

	public void setXrefs(List<String> xrefs) {
		this.xrefs = xrefs;
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("OntologyTermSynonym [name=");
		builder2.append(name);
		builder2.append(", scope=");
		builder2.append(scope);
		builder2.append(", type=");
		builder2.append(type);
		builder2.append(", xrefs=");
		builder2.append(xrefs);
		builder2.append("]");
		return builder2.toString();
	}
	
	

}
