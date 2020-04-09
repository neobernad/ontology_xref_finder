package basf.knowledge.omf.ontology_xref_finder.runnable;

import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JProgressBar;

import org.semanticweb.owlapi.model.IRI;

import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyTerm;
import basf.knowledge.omf.ontology_xref_finder.core.model.XrefMatch;
import basf.knowledge.omf.ontology_xref_finder.core.xrefclient.AbstractXrefClient;

/**
 * RunnableProgress is a Runnable class that is able to execute an
 * AbstractXrefClient and update a JProgressBar while processing the
 * AbstractXrefClient object.
 */
public class RunnableProgress implements Runnable, RunnableProgressPerformer {
	private JProgressBar progressBar = null;
	private AbstractXrefClient xrefClient = null;
	private List<RunnableProgressListener> listeners = null;

	public RunnableProgress(RunnableProgressListener listener, AbstractXrefClient xrefClient,
			JProgressBar progressBar) {
		this.xrefClient = xrefClient;
		this.progressBar = progressBar;
		this.listeners = new LinkedList<RunnableProgressListener>();
		addListener(listener);
	}

	public void addListener(RunnableProgressListener listener) {
		this.listeners.add(listener);
	}

	public void clearListeners() {
		this.listeners.clear();
	}

	@Override
	public void run() {
		xrefClient.getOntology().classesInSignature().forEach(owlClass -> {
			try {
				Stream<XrefMatch> xrefStream = xrefClient.findXrefByLabel(owlClass);
				List<XrefMatch> xrefList = xrefStream.collect(Collectors.toList());
				progressBar.setValue(progressBar.getValue() + 1);
				// xrefClient.addXrefToClass(owlClass, xrefList);
				for (XrefMatch xrefMatch : xrefList) {
					for (IRI xrefIri : xrefMatch.getMatchedIRIs()) {
						List<OntologyTerm> ontologyTerms = xrefClient.getTerm(xrefIri);
						xrefClient.addSynonymsToClass(owlClass, ontologyTerms, xrefIri);
					}
				}
			} catch (SocketException e) {
				progressError(e.getMessage());
				Thread.currentThread().interrupt(); // Die! >:(
			}
		});
		progressFinished();
	}

	@Override
	public void progressFinished() {
		for (RunnableProgressListener listener : listeners) {
			listener.progressFinished();
		}
	}

	@Override
	public void progressError(String msg) {
		System.out.println("Sending msg: " + msg);
		for (RunnableProgressListener listener : listeners) {
			listener.progressError(msg);
		}

	}
}
