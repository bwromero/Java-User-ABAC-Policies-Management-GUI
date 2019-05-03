package usergui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.rsta.ac.java.JarManager;
import org.fife.rsta.ac.java.buildpath.JarLibraryInfo;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import net.miginfocom.swing.MigLayout;
import usergui.model.UserPreference;
import usergui.model.UserPreferencesTableModel;
import usergui.utils.CustomCompletion;
import usergui.view.dialogs.AddUserPreferenceDialog;
import usergui.view.dialogs.EditUserPreferenceDialog;
import usergui.view.dialogs.SearchUserPreferenceDialog;

public class UserPreferencesPanel {

	private JPanel mainPanel;

	private JScrollPane tableSp;
	private JTable preferencesTable;
	private UserPreferencesTableModel preferencesTableModel;

	private JLabel topicFilterExpressionLabel;
	private JLabel parametricPredicateLabel;
	private JLabel validationOutputLbl;

	private JTextField topicFilterExpressionField;
	private RTextScrollPane sp1;
	private RSyntaxTextArea expTextArea;
	private JTextPane validationOutputText;

	private JButton searchPreferenceButton;
	private JButton validationButton;
	private JButton addPreferenceButton;
	private JButton updatePreferenceButton;
	private JButton deletePreferenceButton;
	private JButton exportPreferencesToRedisButton;

	private AddUserPreferenceDialog addUserPreferenceDialog;
	private EditUserPreferenceDialog editUserPreferenceDialog;
	private SearchUserPreferenceDialog searchUserPreferenceDialog;

	private String loggedUserId;

	public UserPreferencesPanel() {
		initComponents();
	}

	protected void initComponents() {
		initTable();
		initDialogs();
		initLabels();
		initTexts();
		initButtons();
		initMainPanel();
	}

	protected void initTable() {
		preferencesTable = new JTable();
		preferencesTable.setFillsViewportHeight(true);

		tableSp = new JScrollPane(preferencesTable);
		tableSp.setPreferredSize(new Dimension(40, 300));
	}

	protected void initDialogs() {
		addUserPreferenceDialog = new AddUserPreferenceDialog(loggedUserId);
		editUserPreferenceDialog = new EditUserPreferenceDialog();
		searchUserPreferenceDialog = new SearchUserPreferenceDialog();
	}

	protected void initLabels() {
		topicFilterExpressionLabel = new JLabel("Topic Filter Expression:");
		parametricPredicateLabel = new JLabel("Parametric Predicate:");
		validationOutputLbl = new JLabel("Validation Output:");
		setBold();
	}

	protected void initTexts() {
		topicFilterExpressionField = new JTextField(40);
		expTextArea = new RSyntaxTextArea(4, 20);
		expTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
		sp1 = new RTextScrollPane(expTextArea);

		validationOutputText = new JTextPane();
		validationOutputText.setEditable(false);
		validationOutputText.setPreferredSize(new Dimension(200, 50));
		validationOutputText.setBackground(Color.LIGHT_GRAY);
	}

	protected void initButtons() {
		exportPreferencesToRedisButton = new JButton("Export");
		validationButton = new JButton("Validate");
		searchPreferenceButton = new JButton("Search...");
		addPreferenceButton = new JButton("Add...");
		updatePreferenceButton = new JButton("Update");
		deletePreferenceButton = new JButton("Delete");
		setActionCommands();
	}

	protected void initMainPanel() {
		mainPanel = new JPanel(new MigLayout("wrap", "[][][]", "[][]"));
		mainPanel.add(createUserPreferencesPanel(), "cell 0 0,grow");
		mainPanel.add(createUserPreferencesEditorPanel(), "cell 1 0 2 1");
		mainPanel.add(searchPreferenceButton, "cell 0 1,alignx left");
		mainPanel.add(createButtonsPanel(), "cell 1 1");
		mainPanel.add(exportPreferencesToRedisButton, "cell 2 1,alignx right");
	}

	protected JPanel createUserPreferencesPanel() {
		JPanel panel = new JPanel(new MigLayout("", "[]", "[][][]"));
		panel.setBorder(BorderFactory.createTitledBorder("Preferences"));
		panel.add(tableSp, "cell 0 0,alignx right,aligny baseline");
		return panel;
	}

	protected JPanel createUserPreferencesEditorPanel() {
		JPanel panel = new JPanel(new MigLayout("fill,wrap 1", "[]", "[][][][][][][]"));
		panel.setBorder(BorderFactory.createTitledBorder("User Preferences Editor"));
		panel.add(topicFilterExpressionLabel, "cell 0 0");
		panel.add(topicFilterExpressionField, "cell 0 1");
		panel.add(parametricPredicateLabel, "cell 0 2");
		panel.add(sp1, "cell 0 3,grow");
		panel.add(validationOutputLbl, "cell 0 4");
		panel.add(validationOutputText, "cell 0 5");
		panel.add(validationButton, "cell 0 6");
		return panel;
	}

	protected JPanel createButtonsPanel() {
		JPanel panel = new JPanel(new MigLayout("wrap 3"));
		panel.add(addPreferenceButton);
		panel.add(updatePreferenceButton);
		panel.add(deletePreferenceButton);
		return panel;
	}

	public UserPreference createPreference() {
		UserPreference preference = new UserPreference();
		preference.setTopicFilterExpression(topicFilterExpressionField.getText());
		preference.setParametricPredicate(expTextArea.getText());
		return preference;
	}

	public void addUserPreferenceToTable(int id) {
		DefaultTableModel model = (DefaultTableModel) preferencesTable.getModel();
		model.addRow(new Object[] { id });
	}

	public void deletePreferenceFromTable(int selectePreferencesTableRow) {
		DefaultTableModel model = (DefaultTableModel) preferencesTable.getModel();
		model.removeRow(selectePreferencesTableRow);
	}

	public UserPreference getSelectedPreferenceFromTable(int selectePreferencesTableRow) {
		UserPreference selectedPreference = new UserPreference();
		String tf = (String) preferencesTable.getModel().getValueAt(selectePreferencesTableRow, 1);
		String exp = (String) preferencesTable.getModel().getValueAt(selectePreferencesTableRow, 2);
		selectedPreference.setTopicFilterExpression(tf);
		selectedPreference.setParametricPredicate(exp);
		return selectedPreference;
	}

	public void cleanTexts() {
		topicFilterExpressionField.setText("");
		expTextArea.setText("");
	}

	public void installCompletionService() {

		LanguageSupportFactory lsf = LanguageSupportFactory.get();

		lsf.register(expTextArea);

		try {
			configureLanguageSupport(expTextArea);
		} catch (Exception e) {

		}
	}

	private void configureLanguageSupport(RSyntaxTextArea textArea) throws IOException {

		CustomCompletion support1 = new CustomCompletion();

		JarManager jarManager = support1.getJarManager();
		jarManager.addCurrentJreClassFileSource();

		jarManager.addClassFileSource(new JarLibraryInfo("CompletionClasses.jar"));

		support1.install(textArea);
	}

	public boolean isParametricPredicateCorrect() {
		if (validationOutputText.getText().equals(">> No syntax errors where found")) {
			return true;
		}
		return false;
	}

	protected void setBold() {
		topicFilterExpressionLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		parametricPredicateLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
	}

	public void addPreferencesPanelButtonsListener(ActionListener e) {
		searchPreferenceButton.addActionListener(e);
		validationButton.addActionListener(e);
		addPreferenceButton.addActionListener(e);
		updatePreferenceButton.addActionListener(e);
		deletePreferenceButton.addActionListener(e);
		exportPreferencesToRedisButton.addActionListener(e);
	}

	public void addSelectedRowListener(MouseListener e) {
		preferencesTable.addMouseListener(e);
	}

	protected void setActionCommands() {
		searchPreferenceButton.setActionCommand("OPEN SEARCH PREFERENCE DIALOG");
		validationButton.setActionCommand("VALIDATE PARAMETRIC PREDICATE");
		addPreferenceButton.setActionCommand("OPEN ADD USER PREFERENCE DIALOG");
		updatePreferenceButton.setActionCommand("UPDATE PREFERENCE");
		deletePreferenceButton.setActionCommand("DELETE PREFERENCE");
		exportPreferencesToRedisButton.setActionCommand("EXPORT PREFERENCES TO REDIS");
	}

	public void addModel(String userId) {
		preferencesTableModel = new UserPreferencesTableModel();
		preferencesTableModel.addData(userId);
		preferencesTable.setModel(preferencesTableModel);
	}

	public void setUserPreferenceData(UserPreference preference) {
		topicFilterExpressionField.setText(preference.getTopicFilterExpression());
		expTextArea.setText(preference.getParametricPredicate());
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public String getUserId() {
		return this.loggedUserId;
	}

	public JTable getPreferencesTable() {
		return this.preferencesTable;
	}

	public UserPreferencesTableModel getPreferencesTableModel() {
		return preferencesTableModel;
	}

	public AddUserPreferenceDialog getAddUserPreferenceDialog() {
		return addUserPreferenceDialog;
	}

	public EditUserPreferenceDialog getEditUserPreferenceDialog() {
		return editUserPreferenceDialog;
	}

	public SearchUserPreferenceDialog getSearchUserPreferenceDialog() {
		return searchUserPreferenceDialog;
	}

	public void setUserId(String id) {
		this.loggedUserId = id;
	}

	public RSyntaxTextArea getExpTextArea() {
		return expTextArea;
	}

	public void setExpTextArea(RSyntaxTextArea expTextArea) {
		this.expTextArea = expTextArea;
	}

	public JTextPane getValidationOutputText() {
		return validationOutputText;
	}

	public void setValidationOutputText(JTextPane validationOutputText) {
		this.validationOutputText = validationOutputText;
	}

	public String getTopicFilterExpression() {
		return topicFilterExpressionField.getText();
	}

	public String getParametricPredicate() {
		return expTextArea.getText();
	}
}