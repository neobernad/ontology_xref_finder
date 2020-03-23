package basf.knowledge.omf.ontology_xref_finder.frontend;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.PrintStream;
import java.util.stream.Stream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
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
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;

import com.formdev.flatlaf.icons.FlatFileViewFileIcon;
import com.formdev.flatlaf.icons.FlatFileViewFloppyDriveIcon;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import basf.knowledge.omf.ontology_xref_finder.core.utils.Constants;
import basf.knowledge.omf.ontology_xref_finder.core.xrefclient.AbstractXrefClient;
import basf.knowledge.omf.ontology_xref_finder.core.xrefclient.OLSXrefClient;
import basf.knowledge.omf.ontology_xref_finder.frontend.utils.CursorController;
import basf.knowledge.omf.ontology_xref_finder.frontend.utils.HTMLUtils;
import basf.knowledge.omf.ontology_xref_finder.output_stream.TextAreaOutputStream;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 8909924824135174494L;
	private static final String APP_NAME = "Ontology crossreference finder";
	private static final Dimension APP_DEFAULT_DIMENSION = new Dimension(600, 400);
	private static final String GITHUB_LINK = HTMLUtils.linkIfy("https://github.com/neobernad/ontology_xref_finder");
	private Container contentPane = getContentPane();

	// Application objects
	private AbstractXrefClient xrefClient = null;
	private File ontologyFile = new File(
			"D:/Program Files/Eclipse JEE/workspace-jee/ontology_xref_finder/core/src/test/resources/test.owl");

	// Shared components (used by other components)
	private JTextArea txtLogArea = new JTextArea();
	private JProgressBar progressBar = new JProgressBar();

	public MainFrame() {
		initialize();
	}

	private void initialize() {
		setTitle(APP_NAME);
		setBounds(100, 100, APP_DEFAULT_DIMENSION.width, APP_DEFAULT_DIMENSION.height);
		setMinimumSize(APP_DEFAULT_DIMENSION);
		setMaximumSize(APP_DEFAULT_DIMENSION);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setResizable(false);

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

		// Tabbed pane

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		// Parameters panel
		{
			JPanel tabParameters = new JPanel();
			tabbedPane.addTab("Parameters", null, tabParameters, "Execution parameters");
			tabParameters.setLayout(new BorderLayout(0, 0));

			JPanel paramPanel = new JPanel();
			JScrollPane scrollPanel = new JScrollPane(paramPanel);
			tabParameters.add(scrollPanel);
			paramPanel.setLayout(new FormLayout(
					new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
							FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, },
					new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

			JLabel lblMaxNumXref = new JLabel("Max. number of xref per term:");
			paramPanel.add(lblMaxNumXref, "2, 2");

			SpinnerModel sm = new SpinnerNumberModel(1, 0, 10, 1); // default value, lower bound, upper bound,increment
																	// by
			JSpinner spinnerMaxNumXref = new JSpinner(sm);
			paramPanel.add(spinnerMaxNumXref, "4, 2");
			scrollPanel.setViewportBorder(new EmptyBorder(5, 5, 5, 5));
			scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			progressBar.setStringPainted(true);
			progressBar.setVisible(false);
			tabParameters.add(progressBar, BorderLayout.SOUTH);
		}

		// Log panel
		{
			JPanel tabLog = new JPanel();
			tabbedPane.addTab("Log", null, tabLog, "Show application logs");
			tabLog.setBorder(new EmptyBorder(0, 0, 0, 0));
			tabLog.setLayout(new BorderLayout());

			txtLogArea.setWrapStyleWord(true);
			txtLogArea.setTabSize(4);
			txtLogArea.setLineWrap(true);
			txtLogArea.setFont(new Font("Consolas", Font.PLAIN, 12));
			txtLogArea.setEditable(false);
			txtLogArea.setText("No log data.");

			// Redirects stdout and stderr to the txtLogArea component
			// if DEV_MODE = FALSE (for release app)
			if (!Constants.DEV_MODE) {
				PrintStream printStream = new PrintStream(new TextAreaOutputStream(txtLogArea));
				System.setOut(printStream);
				System.setErr(printStream);
			} // Else LOG is redirected to standard stdout and stderr

			JScrollPane logScrollPane = new JScrollPane(txtLogArea);
			logScrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			logScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			logScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			tabLog.add(logScrollPane, BorderLayout.CENTER);
		}

		// South panel (with buttons)
		{
			JPanel southPanel = new JPanel();
			contentPane.add(southPanel, BorderLayout.SOUTH);
			southPanel.setLayout(new BorderLayout(0, 0));
			JPanel buttonSeparatorPanel = new JPanel();
			southPanel.add(buttonSeparatorPanel, BorderLayout.NORTH);
			buttonSeparatorPanel.setLayout(new BorderLayout(0, 0));

			JSeparator buttonSeparator = new JSeparator();
			buttonSeparatorPanel.add(buttonSeparator, BorderLayout.NORTH);

			JPanel buttonPanel = new JPanel();
			buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			southPanel.add(buttonPanel, BorderLayout.CENTER);

			JButton btnProcess = new JButton("Process");
			btnProcess.addActionListener(e -> btnProcessActionPerformed(e));

			JButton btnClose = new JButton("Close");
			btnClose.addActionListener(e -> exitActionPerformed());
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

			Component horizontalGlue = Box.createHorizontalGlue();
			buttonPanel.add(horizontalGlue);
			buttonPanel.add(btnProcess);

			Component btnProcessCloseSeparator = Box.createHorizontalStrut(20);
			buttonPanel.add(btnProcessCloseSeparator);
			buttonPanel.add(btnClose);
		}

	}

	private void openOntologyActionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(() -> {
			JFileChooser fileChooser = new JFileChooser();
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				System.out.println("Selected file: " + selectedFile.getAbsolutePath());
			}
		});
	}
	
	private void saveOntologyActionPerformed(ActionEvent e) {
		System.out.println("Saving ontology");
	}

	private void exitActionPerformed() {
		dispose();
	}

	private void btnProcessActionPerformed(ActionEvent e) {
		try {
			CursorController.setWaitCursor(this);
			resetExecution();
			xrefClient = new OLSXrefClient("https://www.ebi.ac.uk/ols/api", ontologyFile, 1);
			progressBar.setMaximum((int) xrefClient.getNumberOfClasses());
			progressBar.setVisible(true);
			txtLogArea.setText("");
			OWLOntology ontology = xrefClient.getOntology();
			JFrame jframe = this;
			
			//TODO: Create a runnable class instead of this crap
			Thread thread = new Thread(new Runnable() {
				JFrame currentFrame = jframe;

				@Override
				public void run() {
					ontology.classesInSignature().forEach(owlClass -> {
						Stream<IRI> xrefs = xrefClient.findXrefByLabel(owlClass);
						xrefClient.addXrefToClass(owlClass, xrefs);
						progressBar.setValue(progressBar.getValue() + 1);
					});
					CursorController.resetCursor(currentFrame);
				}
			});
			thread.start();
		} catch (Exception ex) {
			txtLogArea.append("Unhandled exception: " + ex.getMessage());
		}
	}

	private void resetExecution() {
		xrefClient = null;
		progressBar.setVisible(false);
		progressBar.setValue(0);
		txtLogArea.setText("No log data.");
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
		JOptionPane.showMessageDialog(this, aboutMessage, "About", JOptionPane.PLAIN_MESSAGE);
	}

}
