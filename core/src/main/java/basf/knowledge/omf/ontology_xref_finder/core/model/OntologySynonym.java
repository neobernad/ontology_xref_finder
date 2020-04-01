package basf.knowledge.omf.ontology_xref_finder.core.model;

import java.util.List;

public class OntologySynonym {

	public static class Builder {
		private String name;
		private String scope;
		private String type;
		private List<OntologySynonymXref> xrefs;

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

		public Builder withXrefs(List<OntologySynonymXref> xrefs) {
			this.xrefs = xrefs;
			return this;
		}

		public OntologySynonym build() {
			OntologySynonym synonym = new OntologySynonym();
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
	private List<OntologySynonymXref> xrefs;

	private OntologySynonym() {

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

	public List<OntologySynonymXref> getXrefs() {
		return xrefs;
	}

	public void setXrefs(List<OntologySynonymXref> xrefs) {
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
