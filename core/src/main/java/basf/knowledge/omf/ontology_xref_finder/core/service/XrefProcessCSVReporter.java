package basf.knowledge.omf.ontology_xref_finder.core.service;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import basf.knowledge.omf.ontology_xref_finder.core.model.ReportItem;
import basf.knowledge.omf.ontology_xref_finder.core.model.ReportItemXrefMatch;


public class XrefProcessCSVReporter extends XrefProcessAbstractReporter {
	private static final Logger LOGGER = Logger.getLogger(XrefProcessCSVReporter.class.getName());

	public XrefProcessCSVReporter() {

	}

	public String getReport() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n>>> Report\n");
		if (!xrefFound.isEmpty()) {
			sb.append(">>>>>> I could find cross-references to the following classes:\n");
			sb.append("\n");
			sb.append("label,definition,\"xref uri\",\"xref synonyms\"\n");
			for (ReportItemXrefMatch reportItem : xrefFound) {
				sb.append(reportItem.getInputLabel() + ",\"" + reportItem.getInputDef() + "\","
						+ reportItem.getXrefIri() + ",");
				String synonymListStr = reportItem.getxRefSynonymLabels().stream().map(str -> str.toString()).collect(Collectors.joining(","));
				sb.append("\"" + synonymListStr + "\"\n");
			}
			sb.append("\n");
		}
		if (!noXrefFound.isEmpty()) {
			sb.append(">>>>>> I could NOT find cross-references to the following classes:\n");
			sb.append("\n");
			sb.append("label\n");
			for (ReportItem reportItem : noXrefFound) {
				sb.append(reportItem.getLabel() + "\n");
			}
			sb.append("\n");
		}
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
