package basf.knowledge.omf.ontology_xref_finder.core.service;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.semanticweb.owlapi.model.IRI;

import basf.knowledge.omf.ontology_xref_finder.core.interfaces.IXrefProcessReporter;
import basf.knowledge.omf.ontology_xref_finder.core.model.OntologySynonym;
import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyTerm;
import basf.knowledge.omf.ontology_xref_finder.core.model.ReportItem;
import basf.knowledge.omf.ontology_xref_finder.core.model.ReportItemXrefMatch;


public abstract class XrefProcessAbstractReporter implements IXrefProcessReporter {
	private static final Logger LOGGER = Logger.getLogger(XrefProcessAbstractReporter.class.getName());
	protected List<ReportItemXrefMatch> xrefFound = new LinkedList<ReportItemXrefMatch>();
	protected List<ReportItem> noXrefFound = new LinkedList<ReportItem>();
	protected List<ReportItem> classesWithoutXrefData = new LinkedList<ReportItem>();
	protected List<ReportItem> classesWithoutXrefSynonyms = new LinkedList<ReportItem>();

	public XrefProcessAbstractReporter() {

	}
	
	public void addXrefFound(String sourceLabel, String sourceDef, IRI xrefIri, List<OntologyTerm> ontologyTerms) {
		List<String> synonyms = new LinkedList<String>();
		String xrefLabel = ontologyTerms.get(0).getLabel();
		List<OntologySynonym> xrefSynonyms = ontologyTerms.get(0).getObo_synonym();
		synonyms.add(xrefLabel); // Use owl class Label as synonym
		for (OntologySynonym ontologySynonym : xrefSynonyms) {
			synonyms.add(ontologySynonym.getName());
		}
		xrefFound.add(new ReportItemXrefMatch(sourceLabel, sourceDef, xrefIri, xrefLabel, synonyms));
	}

	public void addNoXrefFound(IRI iri, String label) {
		noXrefFound.add(new ReportItem(iri, label));
	}
	
	public void addClassesWithoutXrefData(IRI iri, String label) {
		classesWithoutXrefData.add(new ReportItem(iri, label));
	}
	
	public void addClassesWithoutXrefSynonyms(IRI iri, String label) {
		classesWithoutXrefSynonyms.add(new ReportItem(iri, label));
	}

	public abstract String getReport();

}
