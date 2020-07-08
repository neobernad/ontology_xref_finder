package basf.knowledge.omf.ontology_xref_finder.core.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;

public class ReportItemXrefMatch {
	private String inputLabel;
	private String inputDef;
	private IRI xrefIri;
	private String xrefLabel;
	private String ontology;
	private Set<String> xRefSynonymLabels;
	private List<String> xRefDefinitions;

	public ReportItemXrefMatch(String inputLabel, String inputDef, IRI xrefIri, String xrefLabel, String ontology,
			Set<String> xRefSynonymLabels, List<String> xrefDefinitions) {
		this.inputLabel = inputLabel;
		this.inputDef = inputDef;
		this.xrefIri = xrefIri;
		this.xrefLabel = xrefLabel;
		this.ontology = ontology;
		this.xRefSynonymLabels = xRefSynonymLabels;
		if (xrefDefinitions == null) {
			this.xRefDefinitions = new LinkedList<String>();
		} else {
			this.xRefDefinitions = xrefDefinitions;
		}
	}

	public String getInputLabel() {
		return inputLabel;
	}

	public void setInputLabel(String inputLabel) {
		this.inputLabel = inputLabel;
	}

	public IRI getXrefIri() {
		return xrefIri;
	}

	public void setXrefIri(IRI xrefIri) {
		this.xrefIri = xrefIri;
	}

	public String getXrefLabel() {
		return xrefLabel;
	}

	public void setXrefLabel(String xrefLabel) {
		this.xrefLabel = xrefLabel;
	}

	public Set<String> getxRefSynonymLabels() {
		return xRefSynonymLabels;
	}

	public void setxRefSynonymLabels(Set<String> xRefSynonymLabels) {
		this.xRefSynonymLabels = xRefSynonymLabels;
	}

	public String getInputDef() {
		return inputDef;
	}

	public void setInputDef(String inputDef) {
		this.inputDef = inputDef;
	}

	public List<String> getxRefDefinitions() {
		return xRefDefinitions;
	}

	public void setxRefDefinitions(List<String> xRefDefinitions) {
		this.xRefDefinitions = xRefDefinitions;
	}

	public String getOntology() {
		return ontology;
	}

	public void setOntology(String ontology) {
		this.ontology = ontology;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReportItemXrefMatch [inputLabel=");
		builder.append(inputLabel);
		builder.append(", inputDef=");
		builder.append(inputDef);
		builder.append(", xrefIri=");
		builder.append(xrefIri);
		builder.append(", xrefLabel=");
		builder.append(xrefLabel);
		builder.append(", ontology=");
		builder.append(ontology);
		builder.append(", xRefSynonymLabels=");
		builder.append(xRefSynonymLabels);
		builder.append(", xRefDefinitions=");
		builder.append(xRefDefinitions);
		builder.append("]");
		return builder.toString();
	}

}
