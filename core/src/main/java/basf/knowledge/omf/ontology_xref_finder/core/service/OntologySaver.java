package basf.knowledge.omf.ontology_xref_finder.core.service;

import java.io.File;

import org.semanticweb.owlapi.model.OWLOntology;

import basf.knowledge.omf.ontology_xref_finder.core.utils.OWLFormat;


public interface OntologySaver {
	public void saveOntology(OWLOntology ontology, File outputFile, OWLFormat format);
	public void saveOntology(OWLOntology ontology, String outputFilePath, OWLFormat format);

}
