package basf.knowledge.omf.ontology_xref_finder.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import basf.knowledge.omf.ontology_xref_finder.frontend.utils.LabelUtils;
import basf.knowledge.omf.ontology_xref_finder.settings.FrontendSettingsValues;
import basf.knowledge.omf.ontology_xref_finder.settings.OLSValidableSetting;
import basf.knowledge.omf.ontology_xref_finder.settings.ValidableSetting;
import basf.knowledge.omf.ontology_xref_finder.settings.ValidableSettingResult;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;

public class SettingsDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1547590758036753768L;
	private static final Dimension DIALOG_DIMENSION = new Dimension(550, 300);
	private static final Dimension TREE_DIMENSION = new Dimension(150, DIALOG_DIMENSION.height);
	private Frame frame = null;
	private JTree tree = null;
	private ValidableSetting validableSetting = null;
	private Container contentPane = getContentPane();

	/** Creates the reusable dialog. */
	public SettingsDialog(Frame frame) {
		super(frame, true);
		this.frame = frame;

		init();

	}

	private void init() {
		setTitle("Settings");
		setMinimumSize(DIALOG_DIMENSION);
		setMaximumSize(DIALOG_DIMENSION);
		setDefaultCloseOperation(HIDE_ON_CLOSE);

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Settings");
		DefaultMutableTreeNode services = new DefaultMutableTreeNode(LabelUtils.SETTINGS_SERVICES);

		DefaultMutableTreeNode services_ols = new DefaultMutableTreeNode(LabelUtils.SETTINGS_SERVICES_OLS);
//        DefaultMutableTreeNode services_bioprtal = new DefaultMutableTreeNode("Bioportal");

		root.add(services);

		services.add(services_ols);
//        services.add(services_bioprtal);

		tree = new JTree(root);
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		// Default selection
		tree.setSelectionPath(new TreePath(services_ols.getPath()));

		// Remove default JTree icons
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
		renderer.setLeafIcon(null);
		renderer.setClosedIcon(null);
		renderer.setOpenIcon(null);

		JScrollPane treeScroll = new JScrollPane(tree);
		treeScroll.setPreferredSize(TREE_DIMENSION);
		treeScroll.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel panel = getSettingsPanel();
		JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScroll, panel);
		contentPane.add(mainPanel, BorderLayout.CENTER);

	}

	public JPanel getSettingsPanel() {
		DefaultMutableTreeNode nodeSelected = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		String nodeName = (String) nodeSelected.getUserObject();

		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new BorderLayout(0, 0));
		settingsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		// North title panel
		{
			JPanel settingsTitlePanel = new JPanel();
			settingsTitlePanel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.LIGHT_GRAY));
			settingsPanel.add(settingsTitlePanel, BorderLayout.NORTH);
			// Left to right flow
			FlowLayout fl_settingsTitlePanel = new FlowLayout(FlowLayout.LEFT, 5, 5);
			settingsTitlePanel.setLayout(fl_settingsTitlePanel);
			JLabel lblSettingsTitle = new JLabel(nodeName);
			Font font = lblSettingsTitle.getFont();
			lblSettingsTitle.setFont(font.deriveFont(font.getStyle() | Font.BOLD));
			settingsTitlePanel.add(lblSettingsTitle);
		}

		// South panel (with buttons)
		contentPane.add(getSouthPanel(), BorderLayout.SOUTH);
		
		validableSetting = new OLSValidableSetting();

		switch (nodeName) {
		case LabelUtils.SETTINGS_SERVICES_OLS:
			settingsPanel.add(validableSetting.getPanel(), BorderLayout.CENTER);
			break;
		default:
			break;
		}

		return settingsPanel;
	}

	/** This method clears the dialog and hides it. */
	public void clearAndHide() {
		setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
	
	private JPanel getSouthPanel() {
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout(0, 0));
		JPanel buttonSeparatorPanel = new JPanel();
		southPanel.add(buttonSeparatorPanel, BorderLayout.NORTH);
		buttonSeparatorPanel.setLayout(new BorderLayout(0, 0));

		JSeparator buttonSeparator = new JSeparator();
		buttonSeparatorPanel.add(buttonSeparator, BorderLayout.NORTH);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		southPanel.add(buttonPanel, BorderLayout.CENTER);

		JButton btnProcess = new JButton();
		btnProcess.setText("Save");
		btnProcess.addActionListener(e -> saveActionPerformed());
		
		JButton btnClose = new JButton();
		btnClose = new JButton("Close");
		btnClose.addActionListener(e -> exitActionPerformed());
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		Component horizontalGlue = Box.createHorizontalGlue();
		buttonPanel.add(horizontalGlue);
		buttonPanel.add(btnProcess);

		Component btnProcessCloseSeparator = Box.createHorizontalStrut(20);
		buttonPanel.add(btnProcessCloseSeparator);
		buttonPanel.add(btnClose);
		return southPanel;
	}

	/**
	 * replace a child of a splitPane with another
	 * 
	 * @param oldChild
	 * @param newChild
	 */
	public void replaceSplitPaneChild(JComponent oldChild, JComponent newChild) {
		JSplitPane parent = (JSplitPane) oldChild.getParent();
		int dividerLocation = parent.getDividerLocation();
		parent.remove(oldChild);
		parent.add(newChild);
		parent.setDividerLocation(dividerLocation);
		newChild.revalidate();
		newChild.repaint();
	}
	
	private void saveActionPerformed() {
		ValidableSettingResult result = validableSetting.validateForm();
		JOptionPane.showMessageDialog(null, result.getMessage(), "Message", result.getStatus());
	}
	
	private void exitActionPerformed() {
		dispose();
	}

}