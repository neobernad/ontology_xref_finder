package basf.knowledge.omf.core;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyMetadata;
import basf.knowledge.omf.ontology_xref_finder.core.parser.ArgumentParser;
import basf.knowledge.omf.ontology_xref_finder.core.xrefclient.AbstractXrefClient;
import basf.knowledge.omf.ontology_xref_finder.core.xrefclient.OLSXrefClient;
import basf.knowledge.omf.utils.Utils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class GetAvailableOntologiesTest extends TestCase {
	private static final Logger LOGGER = Logger.getLogger(GetAvailableOntologiesTest.class.getName());
	private static final ArgumentParser argParser = new ArgumentParser();

	public GetAvailableOntologiesTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(GetAvailableOntologiesTest.class);
	}
	

	public void testGetOlsOntologies() throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
		File ddphenoOnt = Utils.getResourceFile("agro.owl");
		AbstractXrefClient xrefClient = new OLSXrefClient(argParser.getOlsURL(), ddphenoOnt, argParser.getMaxXrefs());
		List<OntologyMetadata> ontologyMetadataList = xrefClient.getAllAvailableOntologies();
		LOGGER.info("Number of ontologies in the OLS: " + ontologyMetadataList.size());
	}
}
