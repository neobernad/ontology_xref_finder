package basf.knowledge.omf.ontology_xref_finder.core.service;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.semanticweb.owlapi.model.IRI;

import basf.knowledge.omf.ontology_xref_finder.core.interfaces.IXrefProcessReporter;
import basf.knowledge.omf.ontology_xref_finder.core.model.OntologySynonym;
import basf.knowledge.omf.ontology_xref_finder.core.model.ReportItem;


public abstract class XrefProcessAbstractReporter implements IXrefProcessReporter {
	private static final Logger LOGGER = Logger.getLogger(XrefProcessAbstractReporter.class.getName());
	protected List<ReportItem> xrefFound = new LinkedList<ReportItem>();
	protected List<ReportItem> noXrefFound = new LinkedList<ReportItem>();
	protected List<ReportItem> classesWithoutXrefData = new LinkedList<ReportItem>();
	protected List<ReportItem> classesWithoutXrefSynonyms = new LinkedList<ReportItem>();

	public XrefProcessAbstractReporter() {

	}
	
	public void addXrefFound(IRI iri, String label, List<OntologySynonym> synonymLabels) {
		List<String> synonyms = new LinkedList<String>();
		synonyms.add(label); // Use owl class Label as synonym
		for (OntologySynonym ontologySynonym : synonymLabels) {
			synonyms.add(ontologySynonym.getName());
		}
		xrefFound.add(new ReportItem(iri, label, synonyms));
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