package basf.knowledge.omf.ontology_xref_finder.core.model;

import java.util.LinkedList;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;

public class ReportItem {
	private IRI iri;
	private String label;
	private List<String> synonymLabels;

	public ReportItem(IRI iri, String label) {
		this(iri, label, new LinkedList<String>());
	}

	public ReportItem(IRI iri, String label, List<String> synonymLabels) {
		this.iri = iri;
		this.label = label;
		this.synonymLabels = synonymLabels;
	}

	public IRI getIri() {
		return iri;
	}

	public String getLabel() {
		return label;
	}

	public List<String> getSynonymLabels() {
		return synonymLabels;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(label);
		return builder.toString();
	}

}
