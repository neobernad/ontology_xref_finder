package basf.knowledge.omf.ontology_xref_finder.runnable;

public interface RunnableProgressPerformer {
	
	public void progressFinished();
	public void addListener(RunnableProgressListener listener);
	public void clearListeners();
	
}
