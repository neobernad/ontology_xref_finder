package basf.knowledge.omf.ontology_xref_finder.core.pojo;

import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import basf.knowledge.omf.ontology_xref_finder.core.interfaces.IPojoMapperOntologyTermSynonym;
import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyTerm;
import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyTermSynonym;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OLSTermsItemSynonym implements IPojoMapperOntologyTermSynonym {
	@JsonbProperty("name")
	private String name;
	@JsonbProperty("scope")
	private String scope;
	@JsonbProperty("type")
	private String type;
	@JsonbProperty("xrefs")
	private List<String> xrefs;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getXrefs() {
		return xrefs;
	}

	public void setXrefs(List<String> xrefs) {
		this.xrefs = xrefs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((scope == null) ? 0 : scope.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((xrefs == null) ? 0 : xrefs.hashCode());
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
		OLSTermsItemSynonym other = (OLSTermsItemSynonym) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (scope == null) {
			if (other.scope != null)
				return false;
		} else if (!scope.equals(other.scope))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (xrefs == null) {
			if (other.xrefs != null)
				return false;
		} else if (!xrefs.equals(other.xrefs))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OLSTermsItemSynonym [name=");
		builder.append(name);
		builder.append(", scope=");
		builder.append(scope);
		builder.append(", type=");
		builder.append(type);
		builder.append(", xrefs=");
		builder.append(xrefs);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public OntologyTermSynonym mapToOntologyTermSynonym() {
		OntologyTermSynonym ontologyTermSynonym = new OntologyTermSynonym.Builder()
				.withName(name)
				.withScope(scope)
				.withType(type)
				.withXrefs(xrefs)
				.build();
		return ontologyTermSynonym;
	}

}
