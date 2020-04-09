package basf.knowledge.omf.ontology_xref_finder.core.model;

import java.util.List;

import org.semanticweb.owlapi.model.IRI;

public class XrefMatch {

	private String literal;
	private List<IRI> matchedIRIs;

	public XrefMatch(String literal, List<IRI> matchedIRIs) {
		this.literal = literal;
		this.matchedIRIs = matchedIRIs;
	}

	public String getLiteral() {
		return literal;
	}

	public void setLiteral(String literal) {
		this.literal = literal;
	}

	public List<IRI> getMatchedIRIs() {
		return matchedIRIs;
	}

	public void setMatchedIRIs(List<IRI> matchedIRIs) {
		this.matchedIRIs = matchedIRIs;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("XrefMatch [literal=");
		builder.append(literal);
		builder.append(", matchedIRIs=");
		builder.append(matchedIRIs);
		builder.append("]");
		return builder.toString();
	}

}
