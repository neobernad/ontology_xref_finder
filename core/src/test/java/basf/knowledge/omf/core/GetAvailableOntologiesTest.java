package basf.knowledge.omf.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyMetadata;
import basf.knowledge.omf.ontology_xref_finder.core.parser.ArgumentParser;
import basf.knowledge.omf.ontology_xref_finder.core.xrefclient.AbstractXrefClient;
import basf.knowledge.omf.ontology_xref_finder.core.xrefclient.OLSXrefClient;
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

	public static InputStream getResourceStream(String fileName) throws IOException {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		InputStream is = classLoader.getResourceAsStream(fileName);
		if (is == null)
			return null;
		return is;
	}
	
	public static File getResourceFile(String fileName) throws IOException {
		InputStream is = getResourceStream(fileName);
		File tempFile = File.createTempFile(fileName, "temp");
		tempFile.deleteOnExit();
		FileOutputStream out = new FileOutputStream(tempFile);
		IOUtils.copy(is, out);
		return tempFile;
	}

	public void testGetOlsOntologies() throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
		File ddphenoOnt = getResourceFile("agro.owl");
		AbstractXrefClient xrefClient = new OLSXrefClient(argParser.getOlsURL(), ddphenoOnt, 2);
		List<OntologyMetadata> ontologyMetadataList = xrefClient.getAllAvailableOntologies();
		LOGGER.info("Number of ontologies in the OLS: " + ontologyMetadataList.size());
	}
}
