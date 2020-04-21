package basf.knowledge.omf.ontology_xref_finder.core.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import basf.knowledge.omf.ontology_xref_finder.core.model.ReportItem;
import basf.knowledge.omf.ontology_xref_finder.core.model.ReportItemXrefMatch;


public class XrefProcessCSVReporter extends XrefProcessAbstractReporter {
	private static final Logger LOGGER = Logger.getLogger(XrefProcessCSVReporter.class.getName());

	public XrefProcessCSVReporter(String outputDirectory) {
		super(outputDirectory);
	}

	public void getReport() throws IOException {
		if (!xrefFound.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			sb.append("label,definition,\"xref uri\",\"xref definition\",\"xref synonyms\"\n");
			for (ReportItemXrefMatch reportItem : xrefFound) {
				sb.append("\"" + reportItem.getInputLabel() + "\",\"" + reportItem.getInputDef() + "\","
						+ reportItem.getXrefIri() + ",");
				String definitionsListStr = reportItem.getxRefDefinitions().stream().map(str -> str.toString()).collect(Collectors.joining(","));
				sb.append("\"" + definitionsListStr + "\",");
				String synonymListStr = reportItem.getxRefSynonymLabels().stream().map(str -> str.toString()).collect(Collectors.joining(","));
				sb.append("\"" + synonymListStr + "\"\n");
			}
			String mappedTermsCSVPath = Paths.get(outputDirectory, mappedTermsFilename + ".csv").toString();
			File mappedTermsCSV = new File(mappedTermsCSVPath);
			mappedTermsCSV.createNewFile();
			FileWriter fw = new FileWriter(mappedTermsCSV.getAbsolutePath(), false);
			String mappedTerms = sb.toString();
			LOGGER.info("Mapped terms:\n" + mappedTerms);
			LOGGER.info("Mapped terms CSV file saved in '" + mappedTermsCSVPath + "'");
			fw.write(mappedTerms);
			fw.close();
		}
		if (!noXrefFound.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			sb.append("label\n");
			for (ReportItem reportItem : noXrefFound) {
				sb.append("\"" +reportItem.getLabel() + "\"\n");
			}
			String unmappedTermsCSVPath = Paths.get(outputDirectory, unmappedTermsFilename + ".csv").toString();
			File unmappedTermsCSV = new File(unmappedTermsCSVPath);
			unmappedTermsCSV.createNewFile();
			FileWriter fw = new FileWriter(unmappedTermsCSV.getAbsolutePath(), false);
			String unmappedTerms = sb.toString();
			LOGGER.info("Unmapped terms:\n" + unmappedTerms);
			LOGGER.info("Mapped terms CSV file saved in '" + unmappedTermsCSVPath + "'");
			fw.write(unmappedTerms);
			fw.close();
		}
			
	}

}
