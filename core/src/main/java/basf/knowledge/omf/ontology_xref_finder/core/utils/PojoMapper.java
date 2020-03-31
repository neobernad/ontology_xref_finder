package basf.knowledge.omf.ontology_xref_finder.core.utils;

import java.util.LinkedList;
import java.util.List;

import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyMetadata;
import basf.knowledge.omf.ontology_xref_finder.core.pojo.OLSOntologies;
import basf.knowledge.omf.ontology_xref_finder.core.pojo.OLSOntologiesEmbedded;
import basf.knowledge.omf.ontology_xref_finder.core.pojo.OLSOntologiesItem;

public enum PojoMapper {
	INSTANCE;
	
	public List<OntologyMetadata> map(OLSOntologiesEmbedded olsOntologiesEmbedded) {
		OLSOntologies olsOntologies = olsOntologiesEmbedded.get_embedded();
		if (olsOntologies == null) {
			throw new RuntimeException("No embedded ontologies. Did the API structure changed?");
		}
		List<OLSOntologiesItem> olsOntologiesItem = olsOntologies.getOntologies();
		if (olsOntologiesItem == null) {
			throw new RuntimeException("No ontologies listed. Did the API structure changed?");
		}
		List<OntologyMetadata> ontologyMetadataList = new LinkedList<OntologyMetadata>();
		for (OLSOntologiesItem ontologyItem : olsOntologiesItem) {
			ontologyMetadataList.add(ontologyItem.mapToOntologyMetadata());
		}
		return ontologyMetadataList;
		
	}
}
