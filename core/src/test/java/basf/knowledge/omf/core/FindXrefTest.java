package basf.knowledge.omf.core;


import java.io.File;
import java.net.SocketException;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.search.EntitySearcher;

import basf.knowledge.omf.ontology_xref_finder.core.xrefclient.AbstractXrefClient;
import basf.knowledge.omf.ontology_xref_finder.core.xrefclient.OLSXrefClient;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class FindXrefTest extends TestCase {
	private static final Logger LOGGER = Logger.getLogger(FindXrefTest.class.getName());
	private static final OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	

	public FindXrefTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(FindXrefTest.class);
	}


	public void testFindXref() throws OWLOntologyCreationException, OWLOntologyStorageException, SocketException {
		File ddphenoOnt = new File("src/test/resources/agro.owl");
		File outputOnt = new File("src/test/resources/output.owl");
		AbstractXrefClient xrefClient = new OLSXrefClient("https://www.ebi.ac.uk/ols/api", ddphenoOnt, 2);
		Optional<OWLClass> currentOWLClass = Optional.empty();
		
		currentOWLClass = xrefClient.getOntology().classesInSignature()
			.filter( owlClass -> owlClass.getIRI().toString().equals("http://purl.obolibrary.org/obo/AGRO_00000002"))
			.findFirst();
		
		Stream<IRI> xrefs = xrefClient.findXrefByLabel(currentOWLClass.get());
		xrefClient.addXrefToClass(currentOWLClass.get(), xrefs);
		LOGGER.info("Annotations of '" + currentOWLClass.get().getIRI().toString() + "'");
		EntitySearcher.getAnnotations(currentOWLClass.get(), xrefClient.getOntology()).forEach(annotation -> {
			LOGGER.info(annotation.toString());
		});
		
		LOGGER.info("Saving test ontology '" + outputOnt.getAbsolutePath() + "'");
		OWLXMLDocumentFormat owlFormat = new OWLXMLDocumentFormat();
		OWLDocumentFormat format = manager.getOntologyFormat(xrefClient.getOntology());
		if (format != null && format.isPrefixOWLDocumentFormat()) {
			owlFormat.copyPrefixesFrom(format.asPrefixOWLDocumentFormat());
		}
		manager.saveOntology(xrefClient.getOntology(), owlFormat, IRI.create(outputOnt.toURI()));
		LOGGER.info("Saved successfully");
	}
}
