package basf.knowledge.omf.ontology_xref_finder.core.parser;

import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.maven.model.Model;
import org.semanticweb.owlapi.manchestersyntax.renderer.ParserException;

class ArgumentsKeys {
	public static final String INPUT_ONT_S = "i";
	public static final String INPUT_ONT_L = "input";
	public static final String OUTPUT_ONT_S = "o";
	public static final String OUTPUT_ONT_L = "output";
	public static final String MAX_XREFS_S = "mx";
	public static final String MAX_XREFS_L = "max_xrefs";
	public static final String CONFIG_FILE_S = "c";
	public static final String CONFIG_FILE_L = "config";
}

class ArgumentsDefaultValues {
	public static final Integer MAX_XREFS_DEFAULT = 1;
}

public class ArgumentParser {
	private static final Logger LOGGER = Logger.getLogger(ArgumentParser.class.getName());
	private String inputOntologyFilename;
	private String outputOntologyFilename;
	private Integer maxXrefs;
	private String configFilename;
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
		this.maxXrefs = ArgumentsDefaultValues.MAX_XREFS_DEFAULT;
		if (cmd.hasOption(ArgumentsKeys.MAX_XREFS_L)) {
			this.maxXrefs = Integer.valueOf(cmd.getOptionValue(ArgumentsKeys.MAX_XREFS_L));
			if (this.maxXrefs != null && this.maxXrefs <= 0) {
				throw new NumberFormatException(
						"Max. number of xrefs '" + ArgumentsKeys.MAX_XREFS_L + "' " + "must be greater than 0");
			}
		}
		
		this.configFilename = null;
		if (cmd.hasOption(ArgumentsKeys.CONFIG_FILE_L)) {
			this.configFilename = cmd.getOptionValue(ArgumentsKeys.CONFIG_FILE_L);
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

		Option max_xrefs = new Option(ArgumentsKeys.MAX_XREFS_S, ArgumentsKeys.MAX_XREFS_L, true,
				"Maximum number of Xrefs to add to a term");
		max_xrefs.setRequired(false);
		options.addOption(max_xrefs);
		
		Option configFile = new Option(ArgumentsKeys.CONFIG_FILE_S, ArgumentsKeys.CONFIG_FILE_L, true,
				"Path to a config file");
		configFile.setRequired(false);
		options.addOption(configFile);
		
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
	
	public String getConfigFilename() {
		return configFilename;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ArgumentParser [inputOntologyFilename=");
		builder.append(inputOntologyFilename);
		builder.append(", outputOntologyFilename=");
		builder.append(outputOntologyFilename);
		builder.append(", maxXrefs=");
		builder.append(maxXrefs);
		builder.append(", configFilename=");
		builder.append(configFilename);
		builder.append(", options=");
		builder.append(options);
		builder.append("]");
		return builder.toString();
	}

}
