package basf.knowledge.omf.ontology_xref_finder.core.pojo;

import javax.json.bind.annotation.JsonbProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OLSSearch {
	@JsonbProperty("response")
	private OLSSearchResponse response;
	
	public OLSSearch() {
	
	}

	public OLSSearchResponse getResponse() {
		return response;
	}

	public void setResponse(OLSSearchResponse response) {
		this.response = response;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((response == null) ? 0 : response.hashCode());
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
		OLSSearch other = (OLSSearch) obj;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		return true;
	}
	

}
