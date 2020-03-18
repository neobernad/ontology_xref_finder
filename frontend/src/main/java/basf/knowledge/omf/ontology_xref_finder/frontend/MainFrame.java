package basf.knowledge.omf.ontology_xref_finder.frontend;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.icons.FlatFileViewFileIcon;
import com.formdev.flatlaf.icons.FlatFileViewFloppyDriveIcon;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import basf.knowledge.omf.ontology_xref_finder.frontend.utils.HTMLUtils;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 8909924824135174494L;
	private static final String APP_NAME = "Ontology crossreference finder";
	private static final String GITHUB_LINK = HTMLUtils.linkIfy("https://github.com/neobernad/ontology_xref_finder");
	private Container contentPane = getContentPane();

	public MainFrame() {
		initialize();
	}

	private void initialize() {
		setTitle(APP_NAME);
		setBounds(100, 100, 450, 300);
		setMinimumSize(new Dimension(450, 300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane.setLayout(new BorderLayout(0, 0));

		/*
		 * MENU
		 */
		JPanel menuPanel = new JPanel();
		contentPane.add(menuPanel, BorderLayout.NORTH);
		menuPanel.setLayout(new BorderLayout(0, 0));

		JMenuBar menuBar = new JMenuBar();
		menuPanel.add(menuBar, BorderLayout.NORTH);
		
		JToolBar toolBarShortcuts = new JToolBar();
		toolBarShortcuts.setFloatable(false);
		menuPanel.add(toolBarShortcuts, BorderLayout.CENTER);
		
		JSeparator toolBarShortcutsSeparator = new JSeparator();
		menuPanel.add(toolBarShortcutsSeparator, BorderLayout.SOUTH);

		// menuFile
		{
			JMenu menuFile = new JMenu("File");
			menuFile.setMnemonic('F');
			menuBar.add(menuFile);
			
			// menuFile items
			{
				JMenuItem menuFileOpen = new JMenuItem();
				menuFileOpen.setText("Open ontology");
				menuFileOpen.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
				menuFileOpen.setMnemonic('O');
				menuFileOpen.addActionListener(e -> openOntologyActionPerformed(e));
				menuFile.add(menuFileOpen);

				JMenuItem menuFileSave = new JMenuItem();
				menuFileSave.setText("Save ontology");
				menuFileSave.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
				menuFileSave.setMnemonic('S');
				menuFileSave.addActionListener(e -> saveOntologyActionPerformed(e));
				menuFileSave.setEnabled(false);
				menuFile.add(menuFileSave);
				menuFile.addSeparator();

				JMenuItem menuFileExit = new JMenuItem("Exit");
				menuFileExit.setText("Exit");
				menuFileExit.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
				menuFileExit.setMnemonic('X');
				menuFileExit.addActionListener(e -> exitActionPerformed());
				menuFile.add(menuFileExit);
			}
			
		}

		// menuView
		{
			JMenu menuView = new JMenu("View");
			menuBar.add(menuView);
			
			// menuView items
			{
				JCheckBoxMenuItem menuViewCBShowTool = new JCheckBoxMenuItem();
				menuViewCBShowTool.setText("Show shortcuts");
				menuViewCBShowTool.setSelected(true);
				menuViewCBShowTool.setMnemonic('T');
				menuViewCBShowTool.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (menuViewCBShowTool.isSelected()) {
							menuViewCBShowTool.setSelected(true);
							toolBarShortcuts.setVisible(true);
							toolBarShortcutsSeparator.setVisible(true);
						} else {
							menuViewCBShowTool.setSelected(false);
							toolBarShortcuts.setVisible(false);
							toolBarShortcutsSeparator.setVisible(false);
						}
						menuViewCBShowTool.revalidate();
					}
				});
				menuView.add(menuViewCBShowTool);
			}
		}

		// menuHelp
		{
			JMenu menuHelp = new JMenu("Help");
			menuBar.add(menuHelp);
			
			// menuHelp items
			{
				JMenuItem menuHelpAbout = new JMenuItem();
				menuHelpAbout.setText("About");
				menuHelpAbout.setMnemonic('A');
				menuHelpAbout.addActionListener(e -> aboutActionPerformed());
				menuHelp.add(menuHelpAbout);
			}
		}

		JButton btnLoadOntology = new JButton();
		btnLoadOntology.setToolTipText("Load ontology");
		btnLoadOntology.setIcon(new FlatFileViewFileIcon());
		btnLoadOntology.addActionListener(e -> openOntologyActionPerformed(e));
		JButton btnSaveOntology = new JButton();
		btnSaveOntology.setToolTipText("Save current ontology");
		btnSaveOntology.setIcon(new FlatFileViewFloppyDriveIcon());
		btnSaveOntology.setEnabled(false);
		toolBarShortcuts.add(btnLoadOntology);
		toolBarShortcuts.add(btnSaveOntology);

		JPanel southPanel = new JPanel();
		contentPane.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new BorderLayout(0, 0));

		JPanel southPanelNorth = new JPanel();
		southPanel.add(southPanelNorth, BorderLayout.NORTH);
		southPanelNorth.setLayout(new BorderLayout(0, 0));

		JSeparator separator = new JSeparator();
		southPanelNorth.add(separator, BorderLayout.NORTH);

		JPanel panelSouthCenter = new JPanel();
		panelSouthCenter.setBorder(new EmptyBorder(5, 5, 5, 5));
		southPanel.add(panelSouthCenter, BorderLayout.CENTER);

		JButton btnProcess = new JButton("Process");

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(e -> exitActionPerformed());
		panelSouthCenter.setLayout(new BoxLayout(panelSouthCenter, BoxLayout.X_AXIS));

		Component horizontalGlue = Box.createHorizontalGlue();
		panelSouthCenter.add(horizontalGlue);
		panelSouthCenter.add(btnProcess);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		panelSouthCenter.add(horizontalStrut);
		panelSouthCenter.add(btnClose);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel tabParameters = new JPanel();
		tabbedPane.addTab("Parameters", null, tabParameters, "Execution parameters");
		tabParameters.setLayout(new BorderLayout(0, 0));
		
		JPanel paramPanel = new JPanel();
		JScrollPane scrollPanel = new JScrollPane(paramPanel);
		tabParameters.add(scrollPanel);
		paramPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblMaxNumXref = new JLabel("Max. number of xref per term:");
		paramPanel.add(lblMaxNumXref, "2, 2");
		
		SpinnerModel sm = new SpinnerNumberModel(0, 0, 10, 1); //default value, lower bound, upper bound,increment by
		JSpinner spinnerMaxNumXref = new JSpinner(sm);
		paramPanel.add(spinnerMaxNumXref, "4, 2");
		scrollPanel.setViewportBorder(new EmptyBorder(5, 5, 5, 5));
		scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JProgressBar progressBar = new JProgressBar();
		tabParameters.add(progressBar, BorderLayout.SOUTH);
		
		JPanel tabLog = new JPanel();
		tabbedPane.addTab("Log", null, tabLog, "Show application logs");
		tabLog.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
	}

	private void openOntologyActionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(() -> {
			JFileChooser fileChooser = new JFileChooser();
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				System.out.println("Selected file: " + selectedFile.getAbsolutePath());
			}
			// JOptionPane.showMessageDialog( this, e.getActionCommand(), "Menu Item",
			// JOptionPane.PLAIN_MESSAGE );
		});
	}

	private void saveOntologyActionPerformed(ActionEvent e) {
		System.out.println("Saving ontology");
	}
	
	private void exitActionPerformed() {
		dispose();
	}
	
	private void aboutActionPerformed() {
		StringBuilder sb = new StringBuilder();
		sb.append("<b>" + APP_NAME + "</b> is a tool developed by the OMF team.<br/>");
		sb.append("For any issue please visit:<br/>");
		sb.append(GITHUB_LINK);
		JTextPane aboutMessage = new JTextPane();
		aboutMessage.setContentType("text/html");
		aboutMessage.setEditable(false);
		aboutMessage.setBackground(null);
		aboutMessage.setBorder(null);
		aboutMessage.setText(HTMLUtils.htmlIfy(sb.toString()));
		JOptionPane.showMessageDialog( this, aboutMessage, "About", JOptionPane.PLAIN_MESSAGE );
	}

}
