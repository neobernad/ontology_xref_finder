package basf.knowledge.omf.ontology_xref_finder.settings;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class OLSValidableSetting extends JPanel implements ValidableSetting {

	private static final long serialVersionUID = -6767444550355350285L;
	private JLabel lblOlsUrl = new JLabel("API URL:");
	private JTextField txtOlsApiUrl = new JTextField();

	public OLSValidableSetting() {
		this.setBorder(new EmptyBorder(5, 5, 5, 5));

		GridBagLayout gbl_settingsConfigPanel = new GridBagLayout();
		gbl_settingsConfigPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_settingsConfigPanel.rowHeights = new int[] { 0, 0 };
		gbl_settingsConfigPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_settingsConfigPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		this.setLayout(gbl_settingsConfigPanel);

		GridBagConstraints gbc_lblOlsUrl = new GridBagConstraints();
		gbc_lblOlsUrl.insets = new Insets(0, 0, 0, 5);
		gbc_lblOlsUrl.anchor = GridBagConstraints.BASELINE_TRAILING;
		gbc_lblOlsUrl.gridx = 0;
		gbc_lblOlsUrl.gridy = 0;
		this.add(lblOlsUrl, gbc_lblOlsUrl);

		txtOlsApiUrl.setText(FrontendSettingsValues.OLS_URL);
		txtOlsApiUrl.setToolTipText("Ontology Lookup Service API endpoint");
		GridBagConstraints gbc_txtOlsApiUrl = new GridBagConstraints();
		gbc_txtOlsApiUrl.anchor = GridBagConstraints.WEST;
		gbc_txtOlsApiUrl.gridx = 1;
		gbc_txtOlsApiUrl.gridy = 0;
		this.add(txtOlsApiUrl, gbc_txtOlsApiUrl);
		txtOlsApiUrl.setColumns(20);
	}

	@Override
	public ValidableSettingResult validateForm() {
		ValidableSettingResult result = new ValidableSettingResult();
		try {
			URL url = new URL(txtOlsApiUrl.getText());
			FrontendSettingsValues.OLS_URL = txtOlsApiUrl.getText();
			result.setMessage("Settings saved");
			result.setStatus(JOptionPane.INFORMATION_MESSAGE);
		} catch (MalformedURLException e) {
			result.setMessage("Error in '" + lblOlsUrl.getText() + "' - " + e.getMessage());
			result.setStatus(JOptionPane.ERROR_MESSAGE);
		}
		return result;
	}

	@Override
	public JPanel getPanel() {
		return this;
	}

}
