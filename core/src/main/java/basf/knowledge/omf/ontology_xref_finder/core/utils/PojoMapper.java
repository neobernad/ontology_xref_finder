package basf.knowledge.omf.ontology_xref_finder.core.utils;

import java.util.LinkedList;
import java.util.List;

import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyMetadata;
import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyTerm;
import basf.knowledge.omf.ontology_xref_finder.core.pojo.OLSOntologies;
import basf.knowledge.omf.ontology_xref_finder.core.pojo.OLSOntologiesEmbedded;
import basf.knowledge.omf.ontology_xref_finder.core.pojo.OLSOntologiesItem;
import basf.knowledge.omf.ontology_xref_finder.core.pojo.OLSTerms;
import basf.knowledge.omf.ontology_xref_finder.core.pojo.OLSTermsEmbedded;
import basf.knowledge.omf.ontology_xref_finder.core.pojo.OLSTermsItem;

public enum PojoMapper {
	INSTANCE;
	
	public List<OntologyMetadata> map(OLSOntologiesEmbedded olsOntologiesEmbedded) {
		OLSOntologies olsOntologies = olsOntologiesEmbedded.get_embedded();
		if (olsOntologies == null) {
			throw new RuntimeException("No embedded ontologies. Did the API structure change?");
		}
		List<OLSOntologiesItem> olsOntologiesItem = olsOntologies.getOntologies();
		if (olsOntologiesItem == null) {
			throw new RuntimeException("No ontologies listed. Did the API structure change?");
		}
		List<OntologyMetadata> ontologyMetadataList = new LinkedList<OntologyMetadata>();
		for (OLSOntologiesItem ontologyItem : olsOntologiesItem) {
			ontologyMetadataList.add(ontologyItem.mapToOntologyMetadata());
		}
		return ontologyMetadataList;
		
	}
	
	public List<OntologyTerm> map(OLSTermsEmbedded olsTermsEmbedded) {
		OLSTerms olsTerms = olsTermsEmbedded.get_embedded();
		if (olsTerms == null) {
			throw new RuntimeException("No embedded terms. Did the API structure change?");
		}
		List<OLSTermsItem> ontologyTermItemList = olsTerms.getTerms();
		if (ontologyTermItemList == null) {
			throw new RuntimeException("No terms listed. Did the API structure change?");
		}
		List<OntologyTerm> ontologyMetadataList = new LinkedList<OntologyTerm>();
		for (OLSTermsItem termItem : ontologyTermItemList) {
			ontologyMetadataList.add(termItem.mapToOntologyTerm());
		}
		return ontologyMetadataList;
		
	}
	
}
