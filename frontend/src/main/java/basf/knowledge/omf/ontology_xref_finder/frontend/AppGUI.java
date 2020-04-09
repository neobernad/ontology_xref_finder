package basf.knowledge.omf.ontology_xref_finder.frontend;
import java.awt.EventQueue;
import java.util.Locale;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

public class AppGUI {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "EN"));
		
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
					System.out.println("Unhandled exception: " + e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}

}
