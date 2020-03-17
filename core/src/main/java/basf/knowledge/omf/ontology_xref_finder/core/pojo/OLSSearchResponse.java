package basf.knowledge.omf.ontology_xref_finder.core.pojo;

import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OLSSearchResponse {
	@JsonbProperty("numFound")
	private Integer numFound;
	@JsonbProperty("start")
	private Integer start;
	@JsonbProperty("docs")
	private List<OLSSearchItem> docs;

	public OLSSearchResponse() {

	}

	public Integer getNumFound() {
		return numFound;
	}

	public void setNumFound(Integer numFound) {
		this.numFound = numFound;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public List<OLSSearchItem> getDocs() {
		return docs;
	}

	public void setDocs(List<OLSSearchItem> docs) {
		this.docs = docs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((docs == null) ? 0 : docs.hashCode());
		result = prime * result + ((numFound == null) ? 0 : numFound.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
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
		OLSSearchResponse other = (OLSSearchResponse) obj;
		if (docs == null) {
			if (other.docs != null)
				return false;
		} else if (!docs.equals(other.docs))
			return false;
		if (numFound == null) {
			if (other.numFound != null)
				return false;
		} else if (!numFound.equals(other.numFound))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

}
