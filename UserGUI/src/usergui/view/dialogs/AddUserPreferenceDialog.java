package usergui.view.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.rsta.ac.java.JarManager;
import org.fife.rsta.ac.java.buildpath.JarLibraryInfo;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.spell.SpellingParser;
import org.fife.ui.rtextarea.RTextScrollPane;

import net.miginfocom.swing.MigLayout;
import usergui.model.UserPreference;
import usergui.utils.CustomCompletion;

public class AddUserPreferenceDialog {

	private JDialog mainDialog;
	private JPanel mainPanel;

	private JLabel userId;
	private JLabel topicFilterExpressionLbl;
	private JLabel parametricPredicateLbl;
	private JLabel validationOutputLbl;
	private JTextField userIdInput;
	private JTextField topicFilterExpressionInput;
	private RTextScrollPane sp1;
	private RSyntaxTextArea expTextArea;
	private JTextPane validationOutputText;
	private JButton validate;
	private JButton okButton;
	private JButton cancelButton;

	private String loggedUserId;

	public AddUserPreferenceDialog(String loggedUserId) {
		this.loggedUserId = loggedUserId;
		try {
			initComponents();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void initComponents() throws IOException {
		initButtons();
		initLabels();
		initTextFields();
		initMainPanel();
		initMainDialog();
	}

	protected void initButtons() {
		validate = new JButton("Validate");
		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");
		setActionCommands();
	}

	protected void initLabels() {
		userId = new JLabel("Your ID:");
		topicFilterExpressionLbl = new JLabel("Topic Filter Expression:");
		parametricPredicateLbl = new JLabel("Parametric Predicate:");
		validationOutputLbl = new JLabel("Validation Output:");
	}

	protected void initTextFields() throws IOException {
		userIdInput = new JTextField(10);
		topicFilterExpressionInput = new JTextField(40);
		userIdInput.setEnabled(false);

		expTextArea = new RSyntaxTextArea(1, 20);
		expTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
		sp1 = new RTextScrollPane(expTextArea);

		validationOutputText = new JTextPane();
		validationOutputText.setEditable(false);
		validationOutputText.setPreferredSize(new Dimension(300, 50));
		validationOutputText.setBackground(Color.LIGHT_GRAY);

		File zip = new File("english_dic.zip");
		boolean usEnglish = true;
		SpellingParser parser = SpellingParser.createEnglishSpellingParser(zip, usEnglish);
		expTextArea.addParser(parser);

	}

	protected void initMainPanel() {
		mainPanel = new JPanel(new MigLayout("fill", "15[]15", "15[][][]20[][]20[]20[][]push[]"));
		mainPanel.add(userId, "cell 0 1");
		mainPanel.add(userIdInput, "cell 0 1");
		mainPanel.add(topicFilterExpressionLbl, "cell 0 3");
		mainPanel.add(topicFilterExpressionInput, "cell 0 4");
		mainPanel.add(createExpEditingPanel(), "cell 0 5,grow");
		mainPanel.add(okButton, "cell 0 8,right");
		mainPanel.add(cancelButton, "cell 0 8,right");
	}

	protected void initMainDialog() {
		mainDialog = new JDialog();
		mainDialog.setTitle("Add an User Preference");
		mainDialog.setBounds(450, 160, 470, 330);
		mainDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainDialog.setModal(true);
		mainDialog.setResizable(false);
		mainDialog.getContentPane().add(mainPanel);
	}

	protected void setActionCommands() {
		validate.setActionCommand("VALIDATE FUNCTION");
		okButton.setActionCommand("CREATE PREFERENCE");
		cancelButton.setActionCommand("CLOSE ADD PREFERENCE DIALOG");
	}

	public JPanel createExpEditingPanel() {
		JPanel panel = new JPanel(new MigLayout("fill", "0[]0", "[][][][]"));
		panel.add(parametricPredicateLbl, "cell 0 0");
		panel.add(sp1, "cell 0 1,grow");
		panel.add(validationOutputLbl, "cell 0 2");
		panel.add(new JScrollPane(validationOutputText), "cell 0 3 ,grow");
		panel.add(validate, "cell 0 3 ,alignx center,aligny center");
		return panel;
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

	public boolean isInputDataEmpty() {
		return userIdInput.getText().isEmpty() || topicFilterExpressionInput.getText().isEmpty()
				|| expTextArea.getText().isEmpty();
	}

	public boolean isParametricPredicateCorrect() {
		if (validationOutputText.getText().equals(">> No syntax errors where found")) {
			return true;
		}
		return false;
	}

	public UserPreference createUserPreference() {
		UserPreference preference = new UserPreference();
		preference.setUserId(userIdInput.getText());
		preference.setTopicFilterExpression(topicFilterExpressionInput.getText());
		preference.setParametricPredicate(expTextArea.getText());
		return preference;
	}

	public void addButtonsListener(ActionListener e) {
		validate.addActionListener(e);
		cancelButton.addActionListener(e);
		okButton.addActionListener(e);
	}

	public void show() {
		mainDialog.setVisible(true);
	}

	public void dispose() {
		mainDialog.dispose();
	}

	public void cleanTexts() {
		topicFilterExpressionInput.setText("");
		expTextArea.setText("");
		validationOutputText.setText("");
	}

	public JDialog getMainDialog() {
		return mainDialog;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public JTextPane getValidationOutputText() {
		return validationOutputText;
	}

	public void setValidationOutputText(JTextPane validationOutputText) {
		this.validationOutputText = validationOutputText;
	}

	public RSyntaxTextArea getExpTextArea() {
		return expTextArea;
	}

	public JButton getFinish() {
		return okButton;
	}

	public JButton getCancel() {
		return cancelButton;
	}

	public String getLoggedUserId() {
		return this.loggedUserId;
	}

	public void setUserId(String userId) {
		userIdInput.setText(String.valueOf(userId));
	}

	public void addAddPreferenceDialogListener(ActionListener e) {
		validate.addActionListener(e);
		okButton.addActionListener(e);
		cancelButton.addActionListener(e);
	}
}