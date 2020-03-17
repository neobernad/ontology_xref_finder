package basf.knowledge.omf.ontology_xref_finder.core;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import basf.knowledge.omf.ontology_xref_finder.core.parser.ArgumentParser;
import basf.knowledge.omf.ontology_xref_finder.core.parser.ConfigParser;
import basf.knowledge.omf.ontology_xref_finder.core.parser.PomParser;
import basf.knowledge.omf.ontology_xref_finder.core.service.OntologySaver;
import basf.knowledge.omf.ontology_xref_finder.core.service.OntologySaverImpl;
import basf.knowledge.omf.ontology_xref_finder.core.utils.OWLFormat;
import basf.knowledge.omf.ontology_xref_finder.core.xrefclient.AbstractXrefClient;
import basf.knowledge.omf.ontology_xref_finder.core.xrefclient.OLSXrefClient;

/**
 * Knowledge systems
 * 
 * @author José Antonio Bernabé Díaz Usage:
 * 
 */

public class App {
	private static final Logger LOGGER = Logger.getLogger(App.class.getName());

	public static void main(String[] args) {
		
		OntologySaver ontologySaver = new OntologySaverImpl();
		ConfigParser configParser = null;
		PomParser pomParser = null;
		AbstractXrefClient xrefClient = null;
		ArgumentParser argParser = null;
		try {
			configParser = new ConfigParser();
			pomParser = new PomParser();
			argParser = new ArgumentParser(pomParser.getPom());
			if (!argParser.parse(args)) {
				return;
			}
			if (argParser.getConfigFilename() == null) {
				configParser.loadDefaultConfig();
			} else {
				configParser.loadConfig(argParser.getConfigFilename());
			}
			
			xrefClient = new OLSXrefClient("https://www.ebi.ac.uk/ols/api", argParser.getInputOntologyFilename(),
					argParser.getMaxXrefs());
		} catch (IOException | XmlPullParserException e) {
			LOGGER.severe("Could not read POM metadata");
			e.printStackTrace();
			return;
		} catch (OWLOntologyCreationException e) {
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			return;
		} catch (URISyntaxException e) {
			LOGGER.info("Could not load configuration file: " + e.getMessage());
			e.printStackTrace();
			return;
		}


		/*
		 * Process Xrefs
		 */
		xrefClient.processOntologyXrefs();
		ontologySaver.saveOntology(xrefClient.getOntology(), argParser.getOutputOntologyFilename(), OWLFormat.OWL);

	}
}
