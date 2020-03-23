package basf.knowledge.omf.ontology_xref_finder.frontend.utils;

import java.awt.Cursor;

import javax.swing.JFrame;

public class CursorController {
	
	public static void setWaitCursor(JFrame jframe) {
		jframe.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}
	
	public static void resetCursor(JFrame jframe) {
		jframe.setCursor(Cursor.getDefaultCursor());
	}

}
