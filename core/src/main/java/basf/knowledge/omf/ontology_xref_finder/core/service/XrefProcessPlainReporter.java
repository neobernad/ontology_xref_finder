package basf.knowledge.omf.ontology_xref_finder.core.service;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import basf.knowledge.omf.ontology_xref_finder.core.model.ReportItem;
import basf.knowledge.omf.ontology_xref_finder.core.model.ReportItemXrefMatch;


public class XrefProcessPlainReporter extends XrefProcessAbstractReporter {
	private static final Logger LOGGER = Logger.getLogger(XrefProcessPlainReporter.class.getName());

	public XrefProcessPlainReporter(String outputDirectory) {
		super(outputDirectory);
	}

	public void getReport() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n>>> Report\n");
		if (!xrefFound.isEmpty()) {
			sb.append(">>>>>> I could find cross-references to the following classes:\n");
			sb.append("\n");
			sb.append("label,definition,\"xref uri\",\"xref definition\",\"xref synonyms\"\n");
			for (ReportItemXrefMatch reportItem : xrefFound) {
				sb.append("\"" + reportItem.getInputLabel() + "\",\"" + reportItem.getInputDef() + "\","
						+ reportItem.getXrefIri() + ",");
				String definitionsListStr = reportItem.getxRefDefinitions().stream().map(str -> str.toString()).collect(Collectors.joining(","));
				sb.append("\"" + definitionsListStr + "\",");
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
				sb.append("\"" +reportItem.getLabel() + "\"\n");
			}
			sb.append("\n");
		}
		String report = sb.toString();
		LOGGER.info(report);
	}

}
