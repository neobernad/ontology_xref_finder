package basf.knowledge.omf.ontology_xref_finder.core.model;

public class OntologyMetadata {
	
	public static class Builder {
		private String id;
		private String title;
		private String version;
		private String preferredPrefix;
		private String description;
		private String homepage;

		public Builder() {

		}

		public Builder withId(String id) {
			this.id = id;
			return this;
		}

		public Builder withTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder withVersion(String version) {
			this.version = version;
			return this;
		}

		public Builder withPreferredPrefix(String preferredPrefix) {
			this.preferredPrefix = preferredPrefix;
			return this;
		}

		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder withHomepage(String homepage) {
			this.homepage = homepage;
			return this;
		}

		public OntologyMetadata build() {
			OntologyMetadata ontologyMetadata = new OntologyMetadata();
			ontologyMetadata.id = this.id;
			ontologyMetadata.title = this.title;
			ontologyMetadata.version = this.version;
			ontologyMetadata.preferredPrefix = this.preferredPrefix;
			ontologyMetadata.description = this.description;
			ontologyMetadata.homepage = this.homepage;
			return ontologyMetadata;
		}

	}

	private String id;
	private String title;
	private String version;
	private String preferredPrefix;
	private String description;
	private String homepage;

	private OntologyMetadata() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPreferredPrefix() {
		return preferredPrefix;
	}

	public void setPreferredPrefix(String preferredPrefix) {
		this.preferredPrefix = preferredPrefix;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

}
