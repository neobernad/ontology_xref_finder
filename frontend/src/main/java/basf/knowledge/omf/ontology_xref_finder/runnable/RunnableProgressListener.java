package basf.knowledge.omf.ontology_xref_finder.runnable;

public interface RunnableProgressListener {
	
	public void progressFinished();
	public void progressError(String msg);
	
}
