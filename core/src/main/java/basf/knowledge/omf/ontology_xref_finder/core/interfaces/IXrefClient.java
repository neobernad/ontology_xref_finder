package basf.knowledge.omf.ontology_xref_finder.core.interfaces;

import java.net.SocketException;
import java.util.List;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;

import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyMetadata;

public interface IXrefClient {
	public Stream<IRI> findXrefByLabel(OWLClass owlClass) throws SocketException;
	public List<OntologyMetadata> getAllAvailableOntologies() throws SocketException;
	public List<OntologyMetadata> getAvailableOntologies(Integer page, Integer size) throws SocketException;
	public long getNumberOfClasses();
}
