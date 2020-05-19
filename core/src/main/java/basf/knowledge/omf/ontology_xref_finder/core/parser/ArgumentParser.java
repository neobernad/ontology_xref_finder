package basf.knowledge.omf.ontology_xref_finder.core.parser;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import basf.knowledge.omf.ontology_xref_finder.core.utils.ArgumentsDefaultValues;

class ArgumentsKeys {
	public static final String INPUT_ONT_S = "i";
	public static final String INPUT_ONT_L = "input";
	public static final String OUTPUT_ONT_S = "o";
	public static final String OUTPUT_ONT_L = "output";
	public static final String MAX_XREFS_S = "mx";
	public static final String MAX_XREFS_L = "max_xrefs";
	public static final String OLS_URL_S = null;
	public static final String OLS_URL_L = "ols_url";
	public static final String ONTOLOGIES_FILTER_S = null; // No short argument for this parameter
	public static final String ONTOLOGIES_FILTER_L = "ontologies";
	public static final String NO_DBXREF_S = null;
	public static final String NO_DBXREF_L = "no_dbxref";
	public static final String EXACT_SEARCH_S = null;
	public static final String EXACT_SEARCH_L = "exact_search";
}

public class ArgumentParser {
	private static final Logger LOGGER = Logger.getLogger(ArgumentParser.class.getName());
	private String inputOntologyFilename;
	private String outputOntologyFilename;
	private String olsURL = ArgumentsDefaultValues.OLS_URL_DEFAULT;
	private Integer maxXrefs = ArgumentsDefaultValues.MAX_XREFS_DEFAULT;
	private List<String> ontologiesFilter = ArgumentsDefaultValues.ONTOLOGIES_FILTER_DEFAULT;
	private Boolean noDbXref = ArgumentsDefaultValues.NO_DBXREF;
	private Boolean exactSearch = ArgumentsDefaultValues.EXACT_SEARCH;
	private Options options = new Options();

	public ArgumentParser() {
		addOptions();
	}

	public boolean parse(String[] args) {
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			LOGGER.severe(e.getMessage());
			formatter.printHelp("", options);
		}
		
		if (cmd.hasOption("h")) {
			formatter.printHelp("ontology_xref_finder", "---", options, "---", true);
			return false;
		}
		
		/*
		 * Required parameters
		 */
		if (!cmd.hasOption(ArgumentsKeys.INPUT_ONT_L)) {
			LOGGER.severe("Required input file '" + ArgumentsKeys.INPUT_ONT_L + "' missing" );
			return false;
		}
		
		if (!cmd.hasOption(ArgumentsKeys.OUTPUT_ONT_L)) {
			LOGGER.severe("Required output file '" + ArgumentsKeys.OUTPUT_ONT_L + "' missing" );
			return false;
		}
		
		this.inputOntologyFilename = cmd.getOptionValue(ArgumentsKeys.INPUT_ONT_L);
		this.outputOntologyFilename = cmd.getOptionValue(ArgumentsKeys.OUTPUT_ONT_L);
		
		
		/*
		 * Optional parameters
		 */
		if (cmd.hasOption(ArgumentsKeys.MAX_XREFS_L)) {
			this.maxXrefs = Integer.valueOf(cmd.getOptionValue(ArgumentsKeys.MAX_XREFS_L));
			if (this.maxXrefs != null && this.maxXrefs <= 0) {
				throw new NumberFormatException(
						"Max. number of xrefs '" + ArgumentsKeys.MAX_XREFS_L + "' " + "must be greater than 0");
			}
		}
		
		this.ontologiesFilter = ArgumentsDefaultValues.ONTOLOGIES_FILTER_DEFAULT;
		if (cmd.hasOption(ArgumentsKeys.ONTOLOGIES_FILTER_L)) {
			String[] arrayOntologies = cmd.getOptionValue(ArgumentsKeys.ONTOLOGIES_FILTER_L).split(",");
			this.ontologiesFilter = Arrays.asList(arrayOntologies);
		}
		
		if (cmd.hasOption(ArgumentsKeys.OLS_URL_L)) {
			this.olsURL = cmd.getOptionValue(ArgumentsKeys.OLS_URL_L);
		}
		
		if (cmd.hasOption(ArgumentsKeys.NO_DBXREF_L)) {
			this.noDbXref = true;
		}
		
		if (cmd.hasOption(ArgumentsKeys.EXACT_SEARCH_L)) {
			this.exactSearch = true;
		}
		
		return true;
	}

	private void addOptions() {
		Option inputOntologyFilePath = new Option(ArgumentsKeys.INPUT_ONT_S, ArgumentsKeys.INPUT_ONT_L, true,
				"Input ontology file path");
		inputOntologyFilePath.setRequired(false);
		options.addOption(inputOntologyFilePath);

		Option outputOntologyFilePath = new Option(ArgumentsKeys.OUTPUT_ONT_S, ArgumentsKeys.OUTPUT_ONT_L, true,
				"Output ontology file path");
		outputOntologyFilePath.setRequired(false);
		options.addOption(outputOntologyFilePath);

		Option maxXrefs = new Option(ArgumentsKeys.MAX_XREFS_S, ArgumentsKeys.MAX_XREFS_L, true,
				"Maximum number of Xrefs to add to a term");
		maxXrefs.setRequired(false);
		options.addOption(maxXrefs);
		
		Option ontologiesFilter = new Option(ArgumentsKeys.ONTOLOGIES_FILTER_S, ArgumentsKeys.ONTOLOGIES_FILTER_L, true,
				"Comma separeted list of ontology names (e.g: doid,clo)");
		ontologiesFilter.setRequired(false);
		options.addOption(ontologiesFilter);
		
		Option olsURL = new Option(ArgumentsKeys.OLS_URL_S, ArgumentsKeys.OLS_URL_L, true,
				"URL to the OWL API (e.g. https://www.ebi.ac.uk/ols/api)");
		olsURL.setRequired(false);
		options.addOption(olsURL);
		
		Option noDbxref = new Option(ArgumentsKeys.NO_DBXREF_S, ArgumentsKeys.NO_DBXREF_L, false,
				"If specified, annotations are NOT annotated with the source cross-referenced class where the annotation comes from.");
		noDbxref.setRequired(false);
		options.addOption(noDbxref);
		
		Option exactSearch = new Option(ArgumentsKeys.EXACT_SEARCH_S, ArgumentsKeys.EXACT_SEARCH_L, false,
				"If specified, exact search is took into account (case insensitive search)");
		exactSearch.setRequired(false);
		options.addOption(exactSearch);
		
		Option help = new Option("h", "help");
		options.addOption(help);
	}

	public String getInputOntologyFilename() {
		return inputOntologyFilename;
	}

	public String getOutputOntologyFilename() {
		return outputOntologyFilename;
	}

	public Integer getMaxXrefs() {
		return maxXrefs;
	}
	

	public static Logger getLogger() {
		return LOGGER;
	}

	public Boolean getNoDbXref() {
		return noDbXref;
	}

	public List<String> getOntologiesFilter() {
		return ontologiesFilter;
	}
	
	public String getOlsURL() {
		return olsURL;
	}
	
	public Boolean getExactSearch() {
		return exactSearch;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ArgumentParser [inputOntologyFilename=");
		builder.append(inputOntologyFilename);
		builder.append(", outputOntologyFilename=");
		builder.append(outputOntologyFilename);
		builder.append(", olsURL=");
		builder.append(olsURL);
		builder.append(", maxXrefs=");
		builder.append(maxXrefs);
		builder.append(", ontologiesFilter=");
		builder.append(ontologiesFilter);
		builder.append(", noDbXref=");
		builder.append(noDbXref);
		builder.append(", options=");
		builder.append(options);
		builder.append("]");
		return builder.toString();
	}

}
