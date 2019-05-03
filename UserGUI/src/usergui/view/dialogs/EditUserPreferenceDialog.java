package usergui.view.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.ShorthandCompletion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import net.miginfocom.swing.MigLayout;
import usergui.model.Attribute;
import usergui.model.JavaScriptFunction;
import usergui.model.User;
import usergui.model.UserPreference;

public class EditUserPreferenceDialog {

	private JDialog mainDialog;
	private JPanel mainPanel;

	private JLabel idLbl;
	private JLabel topicFilterExpressionLbl;
	private JLabel parametricPredicateLbl;
	private JLabel validationOutputLbl;
	private JTextField topicFilterExpressionInput;
	private RTextScrollPane sp1;
	private RSyntaxTextArea expTextArea;
	private JTextPane validationOutputText;
	private JComboBox<String> usersIds;
	private JButton validate;
	private JButton okButton;
	private JButton cancelButton;

	public EditUserPreferenceDialog() {
		try {
			initComponents();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void initComponents() throws IOException {
		initButtons();
		initLabels();
		initComboBoxes();
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
		idLbl = new JLabel("User Id:");
		topicFilterExpressionLbl = new JLabel("Topic Filter Expression:");
		parametricPredicateLbl = new JLabel("Parametric Predicate:");
		validationOutputLbl = new JLabel("Validation Output:");
	}

	protected void initComboBoxes() {
		usersIds = new JComboBox<>();
		usersIds.setEnabled(false);
	}

	protected void initTextFields() throws IOException {
		topicFilterExpressionInput = new JTextField(40);

		expTextArea = new RSyntaxTextArea(1, 20);
		expTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
		sp1 = new RTextScrollPane(expTextArea);

		validationOutputText = new JTextPane();
		validationOutputText.setEditable(false);
		validationOutputText.setPreferredSize(new Dimension(300, 50));
		validationOutputText.setBackground(Color.LIGHT_GRAY);

		/*
		 * File zip = new File("english_dic.zip"); boolean usEnglish = true;
		 * SpellingParser parser =
		 * SpellingParser.createEnglishSpellingParser(zip, usEnglish);
		 * expTextArea.addParser(parser);
		 */

	}

	protected void initMainPanel() {
		mainPanel = new JPanel(new MigLayout("fill", "15[]15", "15[][][]20[][]20[]20[][]push[]"));
		mainPanel.add(idLbl, "cell 0 0");
		mainPanel.add(usersIds, "cell 0 0");
		mainPanel.add(topicFilterExpressionLbl, "cell 0 3");
		mainPanel.add(topicFilterExpressionInput, "cell 0 4");
		mainPanel.add(createExpEditingPanel(), "cell 0 5,grow");
		mainPanel.add(okButton, "cell 0 8,right");
		mainPanel.add(cancelButton, "cell 0 8,right");
	}

	protected void initMainDialog() {
		mainDialog = new JDialog();
		mainDialog.setTitle("Edit an User Preference");
		mainDialog.setBounds(450, 160, 350, 330);
		mainDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainDialog.setModal(true);
		mainDialog.setResizable(false);
		mainDialog.getContentPane().add(mainPanel);
	}

	protected void setActionCommands() {
		validate.setActionCommand("VALIDATE FUNCTION");
		okButton.setActionCommand("UPDATE PREFERENCE");
		cancelButton.setActionCommand("CLOSE EDIT PREFERENCE DIALOG");
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

	public void installCompletionService(List<Attribute> attributes, List<JavaScriptFunction> functions) {
		AutoCompletion ac = new AutoCompletion(createCompletionProvider(attributes, functions));
		ac.install(expTextArea);
		/*
		 * JavaScriptLanguageSupport languageSupport = new
		 * JavaScriptLanguageSupport(); JavaScriptCompletionProvider
		 * completionProvider = new JavaScriptCompletionProvider(new
		 * JarManager(),languageSupport);
		 * completionProvider.setStringCompletionProvider(completionProvider);
		 * 
		 * completionProvider
		 * 
		 * CodeBlock c = new CodeBlock(1);
		 * 
		 * JavaScriptParser javaParser = new JavaScriptParser(languageSupport,
		 * expTextArea); JavaScriptVariableDeclaration subject = new
		 * JavaScriptVariableDeclaration("s", 1, (SourceCompletionProvider)
		 * ac.getCompletionProvider(), new CodeBlock(1));
		 * 
		 * VariableResolver vr = new VariableResolver();
		 * vr.addLocalVariable(subject);
		 * 
		 * javaParser.setVariablesAndFunctions(vr);
		 * 
		 * expTextArea.addParser(javaParser);
		 * 
		 * languageSupport.install(expTextArea);
		 */
	}

	protected CompletionProvider createCompletionProvider(List<Attribute> attributes,
			List<JavaScriptFunction> functions) {

		DefaultCompletionProvider provider = new DefaultCompletionProvider();

		attributes.forEach(attribute -> provider.addCompletion(new BasicCompletion(provider, attribute.getName())));
		functions.forEach(function -> provider.addCompletion(new BasicCompletion(provider, function.getName())));

		provider.addCompletion(new BasicCompletion(provider, "var"));
		provider.addCompletion(new BasicCompletion(provider, "boolean"));
		provider.addCompletion(new BasicCompletion(provider, "true"));
		provider.addCompletion(new BasicCompletion(provider, "false"));
		provider.addCompletion(new BasicCompletion(provider, "while"));
		provider.addCompletion(new BasicCompletion(provider, "abstract"));
		provider.addCompletion(new BasicCompletion(provider, "break"));
		provider.addCompletion(new BasicCompletion(provider, "for"));
		provider.addCompletion(new BasicCompletion(provider, "case"));

		provider.addCompletion(new ShorthandCompletion(provider, "s.", "Name", "Name"));

		return provider;
	}

	public void setComboBoxItems(List<User> users) {
		usersIds.addItem("");
		users.forEach(user -> usersIds.addItem(String.valueOf(user.getId())));
	}

	public void cleanComboBoxs() {
		usersIds.removeAllItems();
	}

	public UserPreference createUserPreference() {
		UserPreference preference = new UserPreference();
		preference.setUserId(usersIds.getSelectedItem().toString());
		preference.setTopicFilterExpression(topicFilterExpressionInput.getText());
		preference.setParametricPredicate(expTextArea.getText());
		return preference;
	}

	public boolean isInputDataEmpty() {
		return topicFilterExpressionInput.getText().isEmpty() || expTextArea.getText().isEmpty();
	}

	public boolean isParametricPredicateCorrect() {
		if (validationOutputText.getText().equals(">> No syntax errors where found")) {
			return true;
		}
		return false;
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
		usersIds.setSelectedItem("");
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

	public JComboBox<String> getUsersIds() {
		return usersIds;
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
}
