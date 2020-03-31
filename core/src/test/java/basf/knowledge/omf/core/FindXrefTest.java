package basf.knowledge.omf.core;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyTerm;
import basf.knowledge.omf.ontology_xref_finder.core.parser.ArgumentParser;
import basf.knowledge.omf.ontology_xref_finder.core.xrefclient.AbstractXrefClient;
import basf.knowledge.omf.ontology_xref_finder.core.xrefclient.OLSXrefClient;
import basf.knowledge.omf.utils.Utils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class FindXrefTest extends TestCase {
	private static final Logger LOGGER = Logger.getLogger(FindXrefTest.class.getName());
	private static final OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	private static final ArgumentParser argParser = new ArgumentParser();

	public FindXrefTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(FindXrefTest.class);
	}

	// @Ignore
	public void testFindXref() throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
		File ddphenoOnt = Utils.getResourceFile("test.owl");

		// File outputOnt = File.createTempFile("output", "owl");
		File outputOnt = new File("C:/Users/Neo/Desktop/output.owl");
		// outputOnt.deleteOnExit();
		AbstractXrefClient xrefClient = new OLSXrefClient(argParser.getOlsURL(), ddphenoOnt, 2);

		xrefClient.getOntology().classesInSignature().forEach(owlClass -> {
			List<IRI> xrefList = null;
			try {
				Stream<IRI> xrefStream = xrefClient.findXrefByLabel(owlClass);
				xrefList = xrefStream.collect(Collectors.toList());
				xrefClient.addXrefToClass(owlClass, xrefList);
			} catch (SocketException e) {
				e.printStackTrace();
			}
			for (IRI iri : xrefList) {
				System.out.println("Processing xref: " + iri.toString());
				try {
					List<OntologyTerm> ontologyTerms = xrefClient.getTerm(iri);
					for (OntologyTerm term : ontologyTerms) {
						System.out.println(term);
					}
				} catch (SocketException e) {
					e.printStackTrace();
				}
			}
		});

		//
		LOGGER.info("Saving test ontology '" + outputOnt.getAbsolutePath() + "'");
		RDFXMLDocumentFormat owlFormat = new RDFXMLDocumentFormat();
		OWLDocumentFormat format = manager.getOntologyFormat(xrefClient.getOntology());
		if (format != null && format.isPrefixOWLDocumentFormat()) {
			owlFormat.copyPrefixesFrom(format.asPrefixOWLDocumentFormat());
		}
		manager.saveOntology(xrefClient.getOntology(), owlFormat, IRI.create(outputOnt.toURI()));
		LOGGER.info("Saved successfully");
	}

}
