package basf.knowledge.omf.ontology_xref_finder.core.pojo;

import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OLSSearchItem {
	@JsonbProperty("id")
	private String id;
	@JsonbProperty("iri")
	private String iri;
	@JsonbProperty("short_form")
	private String short_form;
	@JsonbProperty("obo_id")
	private String obo_id;
	@JsonbProperty("label")
	private String label;
	@JsonbProperty("description")
	private List<String> description;
	@JsonbProperty("ontology_name")
	private String ontology_name;
	@JsonbProperty("ontology_prefix")
	private String ontology_prefix;
	@JsonbProperty("type")
	private String type;

	public OLSSearchItem() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIri() {
		return iri;
	}

	public void setIri(String iri) {
		this.iri = iri;
	}

	public String getShort_form() {
		return short_form;
	}

	public void setShort_form(String short_form) {
		this.short_form = short_form;
	}

	public String getObo_id() {
		return obo_id;
	}

	public void setObo_id(String obo_id) {
		this.obo_id = obo_id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<String> getDescription() {
		return description;
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}

	public String getOntology_name() {
		return ontology_name;
	}

	public void setOntology_name(String ontology_name) {
		this.ontology_name = ontology_name;
	}

	public String getOntology_prefix() {
		return ontology_prefix;
	}

	public void setOntology_prefix(String ontology_prefix) {
		this.ontology_prefix = ontology_prefix;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public boolean isProperty() {
		return type.equals("property");
	}
	
	public boolean isClass() {
		return type.equals("class");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((iri == null) ? 0 : iri.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((obo_id == null) ? 0 : obo_id.hashCode());
		result = prime * result + ((ontology_name == null) ? 0 : ontology_name.hashCode());
		result = prime * result + ((ontology_prefix == null) ? 0 : ontology_prefix.hashCode());
		result = prime * result + ((short_form == null) ? 0 : short_form.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		OLSSearchItem other = (OLSSearchItem) obj;
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
		if (iri == null) {
			if (other.iri != null)
				return false;
		} else if (!iri.equals(other.iri))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (obo_id == null) {
			if (other.obo_id != null)
				return false;
		} else if (!obo_id.equals(other.obo_id))
			return false;
		if (ontology_name == null) {
			if (other.ontology_name != null)
				return false;
		} else if (!ontology_name.equals(other.ontology_name))
			return false;
		if (ontology_prefix == null) {
			if (other.ontology_prefix != null)
				return false;
		} else if (!ontology_prefix.equals(other.ontology_prefix))
			return false;
		if (short_form == null) {
			if (other.short_form != null)
				return false;
		} else if (!short_form.equals(other.short_form))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OLSSearchItem [id=");
		builder.append(id);
		builder.append(", iri=");
		builder.append(iri);
		builder.append(", short_form=");
		builder.append(short_form);
		builder.append(", obo_id=");
		builder.append(obo_id);
		builder.append(", label=");
		builder.append(label);
		builder.append(", description=");
		builder.append(description);
		builder.append(", ontology_name=");
		builder.append(ontology_name);
		builder.append(", ontology_prefix=");
		builder.append(ontology_prefix);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

}
