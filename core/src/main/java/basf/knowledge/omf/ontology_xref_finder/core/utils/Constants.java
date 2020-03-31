package basf.knowledge.omf.ontology_xref_finder.core.utils;

import org.semanticweb.owlapi.model.IRI;

public enum Constants {
	INSTANCE;

	public static IRI RDF_LABEL = IRI.create("http://www.w3.org/2000/01/rdf-schema#label");
	public static IRI GO_HAS_DB_XREF = IRI.create("http://www.geneontology.org/formats/oboInOwl#hasDbXref");
	public static IRI GO_HAS_EXACT_SYN = IRI.create("http://www.geneontology.org/formats/oboInOWL#hasExactSynonym");
	public static IRI GO_HAS_NARROW_SYN = IRI.create("http://www.geneontology.org/formats/oboInOWL#hasNarrowSynonym");
	public static IRI GO_HAS_SYN = IRI.create("http://www.geneontology.org/formats/oboInOwl#hasSynonym");
	
	public static String HAS_EXACT_SYN = "hasExactSynonym";
	public static String HAS_NARROW_SYN = "hasNarrowSynonym";
	public static final boolean DEV_MODE = true;

}
