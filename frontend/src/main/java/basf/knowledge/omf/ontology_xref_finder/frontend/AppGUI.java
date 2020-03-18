package basf.knowledge.omf.ontology_xref_finder.frontend;
import java.awt.EventQueue;

import com.formdev.flatlaf.FlatDarkLaf;


public class AppGUI {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		FlatDarkLaf.install();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					// show frame
					frame.pack();
					frame.setLocationRelativeTo( null );
					frame.setVisible( true );
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
