package basf.knowledge.omf.ontology_xref_finder.core.pojo;

import javax.json.bind.annotation.JsonbProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import basf.knowledge.omf.ontology_xref_finder.core.interfaces.IPojoMapperOntologyMetadata;
import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyMetadata;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OLSOntologiesItem implements IPojoMapperOntologyMetadata {
	@JsonbProperty("ontologyId")
	private String ontologyId;
	@JsonbProperty("loaded")
	private String loaded;
	@JsonbProperty("updated")
	private String updated;
	@JsonbProperty("message")
	private String message;
	@JsonbProperty("version")
	private String version;
	@JsonbProperty("numberOfTerms")
	private String numberOfTerms;
	@JsonbProperty("numberOfProperties")
	private String numberOfProperties;
	@JsonbProperty("numberOfIndividuals")
	private String numberOfIndividuals;
	@JsonbProperty("config")
	private OLSOntologiesItemConfig config;

	public String getOntologyId() {
		return ontologyId;
	}

	public void setOntologyId(String ontologyId) {
		this.ontologyId = ontologyId;
	}

	public String getLoaded() {
		return loaded;
	}

	public void setLoaded(String loaded) {
		this.loaded = loaded;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getNumberOfTerms() {
		return numberOfTerms;
	}

	public void setNumberOfTerms(String numberOfTerms) {
		this.numberOfTerms = numberOfTerms;
	}

	public String getNumberOfProperties() {
		return numberOfProperties;
	}

	public void setNumberOfProperties(String numberOfProperties) {
		this.numberOfProperties = numberOfProperties;
	}

	public String getNumberOfIndividuals() {
		return numberOfIndividuals;
	}

	public void setNumberOfIndividuals(String numberOfIndividuals) {
		this.numberOfIndividuals = numberOfIndividuals;
	}

	public OLSOntologiesItemConfig getConfig() {
		return config;
	}

	public void setConfig(OLSOntologiesItemConfig config) {
		this.config = config;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((config == null) ? 0 : config.hashCode());
		result = prime * result + ((loaded == null) ? 0 : loaded.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((numberOfIndividuals == null) ? 0 : numberOfIndividuals.hashCode());
		result = prime * result + ((numberOfProperties == null) ? 0 : numberOfProperties.hashCode());
		result = prime * result + ((numberOfTerms == null) ? 0 : numberOfTerms.hashCode());
		result = prime * result + ((ontologyId == null) ? 0 : ontologyId.hashCode());
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OLSOntologiesItem other = (OLSOntologiesItem) obj;
		if (config == null) {
			if (other.config != null)
				return false;
		} else if (!config.equals(other.config))
			return false;
		if (loaded == null) {
			if (other.loaded != null)
				return false;
		} else if (!loaded.equals(other.loaded))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (numberOfIndividuals == null) {
			if (other.numberOfIndividuals != null)
				return false;
		} else if (!numberOfIndividuals.equals(other.numberOfIndividuals))
			return false;
		if (numberOfProperties == null) {
			if (other.numberOfProperties != null)
				return false;
		} else if (!numberOfProperties.equals(other.numberOfProperties))
			return false;
		if (numberOfTerms == null) {
			if (other.numberOfTerms != null)
				return false;
		} else if (!numberOfTerms.equals(other.numberOfTerms))
			return false;
		if (ontologyId == null) {
			if (other.ontologyId != null)
				return false;
		} else if (!ontologyId.equals(other.ontologyId))
			return false;
		if (updated == null) {
			if (other.updated != null)
				return false;
		} else if (!updated.equals(other.updated))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OLSOntologiesItem [ontologyId=");
		builder.append(ontologyId);
		builder.append(", loaded=");
		builder.append(loaded);
		builder.append(", updated=");
		builder.append(updated);
		builder.append(", message=");
		builder.append(message);
		builder.append(", version=");
		builder.append(version);
		builder.append(", numberOfTerms=");
		builder.append(numberOfTerms);
		builder.append(", numberOfProperties=");
		builder.append(numberOfProperties);
		builder.append(", numberOfIndividuals=");
		builder.append(numberOfIndividuals);
		builder.append(", config=");
		builder.append(config);
		builder.append("]");
		return builder.toString();
	}


	@Override
	public OntologyMetadata mapToOntologyMetadata() {
		OLSOntologiesItemConfig itemConfig = getConfig();
		OntologyMetadata ontologyMetadata = new OntologyMetadata.Builder()
				.withId(getOntologyId())
				.withTitle(itemConfig.getTitle())
				.withVersion(itemConfig.getVersion())
				.withPreferredPrefix(itemConfig.getPreferredPrefix())
				.withDescription(itemConfig.getDescription())
				.withHomepage(itemConfig.getHomepage())
				.build();
		return ontologyMetadata;
	}

}
