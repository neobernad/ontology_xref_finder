package basf.knowledge.omf.ontology_xref_finder.api.dto;

import java.util.HashSet;
import java.util.Set;

public class ReportXrefMatchItemDto {
	private String xrefLabel;
	private String xrefIri;
	private String xrefOntology;
	private Set<String> xrefSynonyms;
	
	public ReportXrefMatchItemDto(String xrefLabel, String xrefIri, String xrefOntology, Set<String> xrefSynonyms) {
		this.xrefLabel = xrefLabel;
		this.xrefIri = xrefIri;
		this.xrefOntology = xrefOntology;
		this.xrefSynonyms = xrefSynonyms;
	}
	
	public ReportXrefMatchItemDto(String xrefLabel, String xrefIri, String xrefOntology) {
		this.xrefLabel = xrefLabel;
		this.xrefIri = xrefIri;
		this.xrefOntology = xrefOntology;
		this.xrefSynonyms = new HashSet<String>();
	}
	
	public void addSynonym(String synonym) {
		this.xrefSynonyms.add(synonym);
	}


	public String getXrefLabel() {
		return xrefLabel;
	}


	public void setXrefLabel(String xrefLabel) {
		this.xrefLabel = xrefLabel;
	}


	public String getXrefIri() {
		return xrefIri;
	}


	public void setXrefIri(String xrefIri) {
		this.xrefIri = xrefIri;
	}


	public Set<String> getXrefSynonyms() {
		return xrefSynonyms;
	}


	public void setXrefSynonyms(Set<String> xrefSynonyms) {
		this.xrefSynonyms = xrefSynonyms;
	}

	public String getXrefOntology() {
		return xrefOntology;
	}

	public void setXrefOntology(String xrefOntology) {
		this.xrefOntology = xrefOntology;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReportXrefMatchItemDto [xrefLabel=");
		builder.append(xrefLabel);
		builder.append(", xrefIri=");
		builder.append(xrefIri);
		builder.append(", xrefOntology=");
		builder.append(xrefOntology);
		builder.append(", xrefSynonyms=");
		builder.append(xrefSynonyms);
		builder.append("]");
		return builder.toString();
	}
	
	
}
