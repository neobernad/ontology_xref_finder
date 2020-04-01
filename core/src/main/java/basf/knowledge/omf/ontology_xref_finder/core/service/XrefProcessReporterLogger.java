package basf.knowledge.omf.ontology_xref_finder.core.service;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.semanticweb.owlapi.model.IRI;

import basf.knowledge.omf.ontology_xref_finder.core.interfaces.IXrefProcessReporter;

class ReportItem {
	private IRI iri;
	private String label;
	
	public ReportItem(IRI iri, String label) {
		this.iri = iri;
		this.label = label;
	}

	public IRI getIri() {
		return iri;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[iri=");
		builder.append(iri);
		builder.append(", label=");
		builder.append(label);
		builder.append("]");
		return builder.toString();
	}
	

}

public class XrefProcessReporterLogger implements IXrefProcessReporter {
	private static final Logger LOGGER = Logger.getLogger(XrefProcessReporterLogger.class.getName());
	List<ReportItem> noXrefFound = new LinkedList<ReportItem>();
	List<ReportItem> classesWithoutXrefData = new LinkedList<ReportItem>();
	List<ReportItem> classesWithoutXrefSynonyms = new LinkedList<ReportItem>();

	public XrefProcessReporterLogger() {

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

	public String getReport() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n>>> Report\n");
		if (noXrefFound.isEmpty()) {
			sb.append(">>>>>> I could find cross-references to all classes");
		} else {
			sb.append(">>>>>> I could not find cross-references to the following classes:\n");
			sb.append(noXrefFound.toString());
		}
		sb.append("\n");
		if (classesWithoutXrefData.isEmpty()) {
			sb.append(">>>>>> I could find ontology terms for all the classes");
		} else {
			sb.append(">>>>>> I could not find ontology terms for the following classes:\n");
			sb.append(classesWithoutXrefData.toString());
		}
		sb.append("\n");
		if (classesWithoutXrefSynonyms.isEmpty()) {
			sb.append(">>>>>> I could find synonyms to all the classes");
		} else {
			sb.append(">>>>>> I could not find synonyms to the following classes:\n");
			sb.append(classesWithoutXrefSynonyms.toString());
		}
		String report = sb.toString();
		LOGGER.info(report);
		return report;
	}

}
