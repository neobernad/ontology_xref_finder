package basf.knowledge.omf.ontology_xref_finder.settings;

import javax.swing.JPanel;

public interface ValidableSetting {
	public ValidableSettingResult validateForm();
	public JPanel getPanel();
}
