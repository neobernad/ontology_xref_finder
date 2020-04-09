package basf.knowledge.omf.ontology_xref_finder.core.interfaces;

import java.util.List;

import org.semanticweb.owlapi.model.IRI;

import basf.knowledge.omf.ontology_xref_finder.core.model.OntologySynonym;

public interface IXrefProcessReporter {
	public void addXrefFound(String classLabel, IRI iri, String xrefLabel, List<OntologySynonym> synonymLabels);
	public void addNoXrefFound(IRI iri, String label);
	public void addClassesWithoutXrefData(IRI iri, String label);
	public void addClassesWithoutXrefSynonyms(IRI iri, String label);
	public String getReport();
}
