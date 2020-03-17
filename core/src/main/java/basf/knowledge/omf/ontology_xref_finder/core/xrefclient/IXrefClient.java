package basf.knowledge.omf.ontology_xref_finder.core.xrefclient;

import java.util.stream.Stream;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;

public interface IXrefClient {
	public Stream<IRI> findXrefByLabel(OWLClass owlClass);
}