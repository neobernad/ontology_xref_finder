package basf.knowledge.omf.ontology_xref_finder.core.utils;

import org.semanticweb.owlapi.model.IRI;

public enum Constants {
	INSTANCE;

	public static IRI RDF_LABEL = IRI.create("http://www.w3.org/2000/01/rdf-schema#label");
	public static IRI GO_HAS_DX_XREF = IRI.create("http://www.geneontology.org/formats/oboInOwl#hasDbXref");
	public static final boolean DEV_MODE = false;

}
