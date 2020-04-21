package basf.knowledge.omf.ontology_xref_finder.core;

import java.io.IOException;
import java.util.Locale;
import java.util.logging.Logger;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import basf.knowledge.omf.ontology_xref_finder.core.interfaces.IOntologySaver;
import basf.knowledge.omf.ontology_xref_finder.core.interfaces.IXrefProcessReporter;
import basf.knowledge.omf.ontology_xref_finder.core.parser.ArgumentParser;
import basf.knowledge.omf.ontology_xref_finder.core.service.OntologySaver;
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
		Locale.setDefault(new Locale("en", "EN"));
		
		IOntologySaver ontologySaver = new OntologySaver();
		AbstractXrefClient xrefClient = null;
		ArgumentParser argParser = null;
		try {
			argParser = new ArgumentParser();
			if (!argParser.parse(args)) {
				return;
			}
			
			xrefClient = new OLSXrefClient(argParser.getOlsURL(), argParser.getInputOntologyFilename(),
					argParser.getMaxXrefs());
			// TODO: Wrap search configuration inside an object, instead of handling sevaral setters
			xrefClient.setOntologiesFilter(argParser.getOntologiesFilter());
			xrefClient.setNoDbXref(argParser.getNoDbXref());
			xrefClient.setExactMatch(argParser.getExactSearch());
		} catch (OWLOntologyCreationException e) {
			LOGGER.info("Could not load ontology: " + e.getMessage());
			e.printStackTrace();
			return;
		}

		/*
		 * Process Xrefs
		 */
		xrefClient.processOntologyXrefs();
		IXrefProcessReporter report = xrefClient.getXrefProcessReporter();
		try {
			report.getReport();
		} catch (IOException e) {
			LOGGER.info("Could not generate report: " + e.getMessage());
			e.printStackTrace();
			return;
		}
		ontologySaver.saveOntology(xrefClient.getOntology(), argParser.getOutputOntologyFilename(), OWLFormat.OWL);

	}
}
