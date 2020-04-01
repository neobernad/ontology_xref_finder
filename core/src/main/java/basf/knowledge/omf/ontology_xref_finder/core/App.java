package basf.knowledge.omf.ontology_xref_finder.core;

import java.util.logging.Logger;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import basf.knowledge.omf.ontology_xref_finder.core.interfaces.IOntologySaver;
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
			xrefClient.setOntologiesFilter(argParser.getOntologiesFilter());
			xrefClient.setNoDbXref(argParser.getNoDbXref());
		} catch (OWLOntologyCreationException e) {
			LOGGER.info(e.getMessage());
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
