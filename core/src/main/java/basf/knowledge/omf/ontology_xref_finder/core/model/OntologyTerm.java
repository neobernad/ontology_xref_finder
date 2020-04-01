package basf.knowledge.omf.ontology_xref_finder.core.model;

import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

import basf.knowledge.omf.ontology_xref_finder.core.pojo.OLSSynonym;

public class OntologyTerm {

	public static class Builder {
		private String iri;
		private String label;
		private List<String> description;
		private String ontology_name;
		private String ontology_prefix;
		private String short_form;
		private String obo_id;
		private List<OntologySynonym> obo_synonym;

		public Builder() {

		}

		public Builder withIri(String iri) {
			this.iri = iri;
			return this;
		}

		public Builder withLabel(String label) {
			this.label = label;
			return this;
		}

		public Builder withDescription(List<String> description) {
			this.description = description;
			return this;
		}

		public Builder withOntologyName(String ontology_name) {
			this.ontology_name = ontology_name;
			return this;
		}

		public Builder withOntologyPrefix(String ontology_prefix) {
			this.ontology_prefix = ontology_prefix;
			return this;
		}

		public Builder withShortForm(String short_form) {
			this.short_form = short_form;
			return this;
		}

		public Builder withOboId(String obo_id) {
			this.obo_id = obo_id;
			return this;
		}

		public Builder withOboSynonyms(List<OntologySynonym> obo_synonym) {
			this.obo_synonym = obo_synonym;
			return this;
		}

		public OntologyTerm build() {
			OntologyTerm ontologyTerm = new OntologyTerm();
			ontologyTerm.iri = this.iri;
			ontologyTerm.label = this.label;
			ontologyTerm.description = this.description;
			ontologyTerm.ontology_name = this.ontology_name;
			ontologyTerm.ontology_prefix = this.ontology_prefix;
			ontologyTerm.short_form = this.short_form;
			ontologyTerm.obo_id = this.obo_id;
			ontologyTerm.obo_synonym = this.obo_synonym;
			return ontologyTerm;
		}

	}

	private String iri;
	private String label;
	private List<String> description;
	private String ontology_name;
	private String ontology_prefix;
	private String short_form;
	private String obo_id;
	private List<OntologySynonym> obo_synonym;

	private OntologyTerm() {

	}

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

	public List<OntologySynonym> getObo_synonym() {
		return obo_synonym;
	}

	public void setObo_synonym(List<OntologySynonym> obo_synonym) {
		this.obo_synonym = obo_synonym;
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("OntologyTerm [iri=");
		builder2.append(iri);
		builder2.append(", label=");
		builder2.append(label);
		builder2.append(", description=");
		builder2.append(description);
		builder2.append(", ontology_name=");
		builder2.append(ontology_name);
		builder2.append(", ontology_prefix=");
		builder2.append(ontology_prefix);
		builder2.append(", short_form=");
		builder2.append(short_form);
		builder2.append(", obo_id=");
		builder2.append(obo_id);
		builder2.append(", obo_synonym=");
		builder2.append(obo_synonym);
		builder2.append("]");
		return builder2.toString();
	}
	
	

}
