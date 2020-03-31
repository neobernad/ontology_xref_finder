package basf.knowledge.omf.ontology_xref_finder.core.pojo;

import java.util.LinkedList;
import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import basf.knowledge.omf.ontology_xref_finder.core.interfaces.IPojoMapperOntologyTerm;
import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyMetadata;
import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyTerm;
import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyTermSynonym;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OLSTermsItem implements IPojoMapperOntologyTerm {
	@JsonbProperty("iri")
	private String iri;
	@JsonbProperty("label")
	private String label;
	@JsonbProperty("description")
	private List<String> description;
	@JsonbProperty("ontology_name")
	private String ontology_name;
	@JsonbProperty("ontology_prefix")
	private String ontology_prefix;
	@JsonbProperty("short_form")
	private String short_form;
	@JsonbProperty("obo_id")
	private String obo_id;
	@JsonbProperty("obo_synonym")
	private List<OLSTermsItemSynonym> obo_synonym;

	public String getIri() {
		return iri;
	}

	public void setIri(String iri) {
		this.iri = iri;
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

	public List<OLSTermsItemSynonym> getObo_synonym() {
		return obo_synonym;
	}

	public void setObo_synonym(List<OLSTermsItemSynonym> obo_synonym) {
		this.obo_synonym = obo_synonym;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((iri == null) ? 0 : iri.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((obo_id == null) ? 0 : obo_id.hashCode());
		result = prime * result + ((obo_synonym == null) ? 0 : obo_synonym.hashCode());
		result = prime * result + ((ontology_name == null) ? 0 : ontology_name.hashCode());
		result = prime * result + ((ontology_prefix == null) ? 0 : ontology_prefix.hashCode());
		result = prime * result + ((short_form == null) ? 0 : short_form.hashCode());
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
		OLSTermsItem other = (OLSTermsItem) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
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
		if (obo_synonym == null) {
			if (other.obo_synonym != null)
				return false;
		} else if (!obo_synonym.equals(other.obo_synonym))
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
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OLSTermsItem [iri=");
		builder.append(iri);
		builder.append(", label=");
		builder.append(label);
		builder.append(", description=");
		builder.append(description);
		builder.append(", ontology_name=");
		builder.append(ontology_name);
		builder.append(", ontology_prefix=");
		builder.append(ontology_prefix);
		builder.append(", short_form=");
		builder.append(short_form);
		builder.append(", obo_id=");
		builder.append(obo_id);
		builder.append(", obo_synonym=");
		builder.append(obo_synonym);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public OntologyTerm mapToOntologyTerm() {
		List<OntologyTermSynonym> synonyms = new LinkedList<OntologyTermSynonym>();
		if (obo_synonym != null) {
			for (OLSTermsItemSynonym termSynonym : obo_synonym) {
				synonyms.add(termSynonym.mapToOntologyTermSynonym());
			}
		}
		
		OntologyTerm ontologyTerm = new OntologyTerm.Builder()
				.withIri(iri)
				.withLabel(label)
				.withDescription(description)
				.withShortForm(short_form)
				.withOboId(obo_id)
				.withOntologyName(ontology_name)
				.withOntologyPrefix(ontology_prefix)
				.withOboSynonyms(synonyms)
				.build();
		return ontologyTerm;
	}

}
