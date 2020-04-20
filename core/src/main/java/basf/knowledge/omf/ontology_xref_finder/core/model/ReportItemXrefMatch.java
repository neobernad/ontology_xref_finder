package basf.knowledge.omf.ontology_xref_finder.core.model;

import java.util.List;

import org.semanticweb.owlapi.model.IRI;

public class ReportItemXrefMatch {
	private String inputLabel;
	private String inputDef;
	private IRI xrefIri;
	private String xrefLabel;
	private List<String> xRefSynonymLabels;
	
	public ReportItemXrefMatch(String inputLabel, String inputDef, IRI xrefIri, String xrefLabel, List<String> xRefSynonymLabels) {
		this.inputLabel = inputLabel;
		this.inputDef = inputDef;
		this.xrefIri = xrefIri;
		this.xrefLabel = xrefLabel;
		this.xRefSynonymLabels = xRefSynonymLabels;
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

	public List<String> getxRefSynonymLabels() {
		return xRefSynonymLabels;
	}

	public void setxRefSynonymLabels(List<String> xRefSynonymLabels) {
		this.xRefSynonymLabels = xRefSynonymLabels;
	}
	
	public String getInputDef() {
		return inputDef;
	}

	public void setInputDef(String inputDef) {
		this.inputDef = inputDef;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReportItemXrefMatch [inputLabel=");
		builder.append(inputLabel);
		builder.append(", xrefIri=");
		builder.append(xrefIri);
		builder.append(", xrefLabel=");
		builder.append(xrefLabel);
		builder.append(", xRefSynonymLabels=");
		builder.append(xRefSynonymLabels);
		builder.append("]");
		return builder.toString();
	}

}
