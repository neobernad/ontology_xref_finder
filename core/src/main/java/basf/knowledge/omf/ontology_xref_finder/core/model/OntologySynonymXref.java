package basf.knowledge.omf.ontology_xref_finder.core.model;

public class OntologySynonymXref {
	
	public static class Builder {
		private String database;
		private String id;
		private String description;
		private String url;

		public Builder() {

		}

		public Builder withDatabase(String database) {
			this.database = database;
			return this;
		}

		public Builder withId(String id) {
			this.id = id;
			return this;
		}


		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}
		
		public Builder withUrl(String url) {
			this.url = url;
			return this;
		}
		
		public OntologySynonymXref build() {
			OntologySynonymXref synonymXref = new OntologySynonymXref();
			synonymXref.database = this.database;
			synonymXref.id = this.id;
			synonymXref.description = this.description;
			synonymXref.url = this.url;
			return synonymXref;
		}

	}

	private String database;
	private String id;
	private String description;
	private String url;

	private OntologySynonymXref() {

	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("OntologySynonymXref [database=");
		builder2.append(database);
		builder2.append(", id=");
		builder2.append(id);
		builder2.append(", description=");
		builder2.append(description);
		builder2.append(", url=");
		builder2.append(url);
		builder2.append("]");
		return builder2.toString();
	}


}
