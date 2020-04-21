package basf.knowledge.omf.ontology_xref_finder.core.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;

public class ReportItemXrefMatch {
	private String inputLabel;
	private String inputDef;
	private IRI xrefIri;
	private String xrefLabel;
	private List<String> xRefSynonymLabels;
	private List<String> xRefDefinitions;
	
	public ReportItemXrefMatch(String inputLabel, String inputDef, IRI xrefIri, 
			String xrefLabel, List<String> xRefSynonymLabels, List<String> xrefDefinitions) {
		this.inputLabel = inputLabel;
		this.inputDef = inputDef;
		this.xrefIri = xrefIri;
		this.xrefLabel = xrefLabel;
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
	
	public List<String> getxRefDefinitions() {
		return xRefDefinitions;
	}

	public void setxRefDefinitions(List<String> xRefDefinitions) {
		this.xRefDefinitions = xRefDefinitions;
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
