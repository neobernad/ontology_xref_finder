package basf.knowledge.omf.ontology_xref_finder.core.pojo;

import javax.json.bind.annotation.JsonbProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OLSOntologiesEmbedded {
	@JsonbProperty("_embedded")
	private OLSOntologies _embedded;
	
	public OLSOntologiesEmbedded() {
	
	}

	public OLSOntologies get_embedded() {
		return _embedded;
	}

	public void set_embedded(OLSOntologies _embedded) {
		this._embedded = _embedded;
	}

}
