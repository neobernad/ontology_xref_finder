package basf.knowledge.omf.ontology_xref_finder.core.pojo;

import javax.json.bind.annotation.JsonbProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OLSTermsEmbedded {
	@JsonbProperty("_embedded")
	private OLSTerms _embedded;
	
	public OLSTermsEmbedded() {
	
	}

	public OLSTerms get_embedded() {
		return _embedded;
	}

	public void set_embedded(OLSTerms _embedded) {
		this._embedded = _embedded;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_embedded == null) ? 0 : _embedded.hashCode());
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
		OLSTermsEmbedded other = (OLSTermsEmbedded) obj;
		if (_embedded == null) {
			if (other._embedded != null)
				return false;
		} else if (!_embedded.equals(other._embedded))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OLSTermEmbedded [_embedded=");
		builder.append(_embedded);
		builder.append("]");
		return builder.toString();
	}
	
}
