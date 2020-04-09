package basf.knowledge.omf.ontology_xref_finder.core.interfaces;

import java.net.SocketException;
import java.util.List;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;

import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyMetadata;
import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyTerm;
import basf.knowledge.omf.ontology_xref_finder.core.model.XrefMatch;

public interface IXrefClient {
	public Stream<XrefMatch> findXrefByLabel(OWLClass owlClass) throws SocketException;
	public abstract List<OntologyTerm> getTerm(IRI iri) throws SocketException;
	public List<OntologyMetadata> getAllAvailableOntologies() throws SocketException;
	public List<OntologyMetadata> getAvailableOntologies(Integer page, Integer size) throws SocketException;
	public long getNumberOfClasses();
}
