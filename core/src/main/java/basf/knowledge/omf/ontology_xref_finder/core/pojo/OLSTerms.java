package basf.knowledge.omf.ontology_xref_finder.core.pojo;

import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OLSTerms {
	@JsonbProperty("terms")
	private List<OLSTermsItem> terms;
	
	public OLSTerms() {
	
	}

	public List<OLSTermsItem> getTerms() {
		return terms;
	}

	public void setTerms(List<OLSTermsItem> terms) {
		this.terms = terms;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((terms == null) ? 0 : terms.hashCode());
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
		OLSTerms other = (OLSTerms) obj;
		if (terms == null) {
			if (other.terms != null)
				return false;
		} else if (!terms.equals(other.terms))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OLSTerms [terms=");
		builder.append(terms);
		builder.append("]");
		return builder.toString();
	}


}
