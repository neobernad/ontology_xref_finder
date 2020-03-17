package basf.knowledge.omf.ontology_xref_finder.core.service;

import java.io.File;
import java.util.logging.Logger;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import basf.knowledge.omf.ontology_xref_finder.core.utils.OWLFormat;


public class OntologySaverImpl implements OntologySaver {
	private static final Logger LOGGER = Logger.getLogger(OntologySaverImpl.class.getName());

	protected final OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

	public void saveOntology(OWLOntology ontology, File outputFile, OWLFormat outputFormat) {
		LOGGER.info("Saving ontology...");
		OWLDocumentFormat format = getDocumentFormat(outputFormat);
		
		try {
			manager.saveOntology(ontology, format, IRI.create(outputFile.toURI()));
		} catch (OWLOntologyStorageException e) {
			LOGGER.severe("Could not save ontology '" + outputFile.getAbsolutePath() + "'");
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
		LOGGER.info("Ontology saved in '" + outputFile.getAbsolutePath() + "'");

	}
	
	public void saveOntology(OWLOntology ontology, String outputFilePath, OWLFormat format) {
		saveOntology(ontology, new File(outputFilePath), format);
	}

	private OWLDocumentFormat getDocumentFormat(OWLFormat format) {
		OWLDocumentFormat documentFormat = null;
		switch (format) {
		case OWL:
			documentFormat = new OWLXMLDocumentFormat();
			break;
		case RDF:
			documentFormat = new RDFXMLDocumentFormat();
			break;
		case TURTLE:
			documentFormat = new TurtleDocumentFormat();
			break;
		case OBO:
			documentFormat = new OBODocumentFormat(); // OBO spec. 1.4
			break;
		default:
			String error = "Unknown format '" + format.name() + "'";
			LOGGER.severe(error);
			throw new RuntimeException(error);
		}
		return documentFormat;
	}

}
