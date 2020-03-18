package basf.knowledge.omf.ontology_xref_finder.frontend.utils;

public class HTMLUtils {
	
	private static final String LABEL_TEXT = "For further information visit:";
	private static final String A_VALID_LINK = "http://stackoverflow.com";
	private static final String A_HREF = "<a href=\"";
	private static final String HREF_CLOSED = "\">";
	private static final String HREF_END = "</a>";
	private static final String HTML = "<html>";
	private static final String HTML_END = "</html>";
	
	public static String htmlIfy(String s) {
	    return HTML.concat(s).concat(HTML_END);
	}

	public static String linkIfy(String s) {
	    return A_HREF.concat(s).concat(HREF_CLOSED).concat(s).concat(HREF_END);
	}
}
