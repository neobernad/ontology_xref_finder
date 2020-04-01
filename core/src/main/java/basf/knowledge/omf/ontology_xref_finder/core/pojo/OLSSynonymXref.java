package basf.knowledge.omf.ontology_xref_finder.core.pojo;

import javax.json.bind.annotation.JsonbProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import basf.knowledge.omf.ontology_xref_finder.core.interfaces.IPojoMapperOntologySynonymXref;
import basf.knowledge.omf.ontology_xref_finder.core.model.OntologySynonymXref;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OLSSynonymXref implements IPojoMapperOntologySynonymXref {
	@JsonbProperty("database")
	private String database;
	@JsonbProperty("id")
	private String id;
	@JsonbProperty("description")
	private String description;
	@JsonbProperty("url")
	private String url;
	
	
	
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
	


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OLSSynonymXref [database=");
		builder.append(database);
		builder.append(", id=");
		builder.append(id);
		builder.append(", description=");
		builder.append(description);
		builder.append(", url=");
		builder.append(url);
		builder.append("]");
		return builder.toString();
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((database == null) ? 0 : database.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		OLSSynonymXref other = (OLSSynonymXref) obj;
		if (database == null) {
			if (other.database != null)
				return false;
		} else if (!database.equals(other.database))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}



	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public OntologySynonymXref mapToOntologySynonymXref() {
		OntologySynonymXref synonymXref = new OntologySynonymXref.Builder()
				.withDatabase(database)
				.withId(id)
				.withDescription(description)
				.withUrl(url)
				.build();
		return synonymXref;
	}

}
