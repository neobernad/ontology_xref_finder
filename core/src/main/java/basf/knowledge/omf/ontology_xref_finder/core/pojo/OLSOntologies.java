package basf.knowledge.omf.ontology_xref_finder.core.pojo;

import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OLSOntologies {
	@JsonbProperty("ontologies")
	private List<OLSOntologiesItem> ontologies;
	
	public OLSOntologies() {
	
	}

	public List<OLSOntologiesItem> getOntologies() {
		return ontologies;
	}

	public void setOntologies(List<OLSOntologiesItem> ontologies) {
		this.ontologies = ontologies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ontologies == null) ? 0 : ontologies.hashCode());
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
		OLSOntologies other = (OLSOntologies) obj;
		if (ontologies == null) {
			if (other.ontologies != null)
				return false;
		} else if (!ontologies.equals(other.ontologies))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OLSOntologies [ontologies=");
		builder.append(ontologies);
		builder.append("]");
		return builder.toString();
	}
	

}
