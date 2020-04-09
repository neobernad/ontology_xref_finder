package basf.knowledge.omf.ontology_xref_finder.core.service;

import java.util.logging.Logger;


public class XrefProcessPlainReporter extends XrefProcessAbstractReporter {
	private static final Logger LOGGER = Logger.getLogger(XrefProcessPlainReporter.class.getName());

	public XrefProcessPlainReporter() {

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
//		if (classesWithoutXrefData.isEmpty()) {
//			sb.append(">>>>>> I could find ontology terms for all the classes");
//		} else {
//			sb.append(">>>>>> I could not find ontology terms for the following classes:\n");
//			sb.append(classesWithoutXrefData.toString());
//		}
//		sb.append("\n");
//		if (classesWithoutXrefSynonyms.isEmpty()) {
//			sb.append(">>>>>> I could find synonyms to all the classes");
//		} else {
//			sb.append(">>>>>> I could not find synonyms to the following classes:\n");
//			sb.append(classesWithoutXrefSynonyms.toString());
//		}
		String report = sb.toString();
		LOGGER.info(report);
		return report;
	}

}
