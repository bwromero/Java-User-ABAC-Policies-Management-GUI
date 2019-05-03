package usergui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextPane;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import usergui.db.dao.services.ClientAttributeService;
import usergui.db.dao.services.EnviromentAttributeService;
import usergui.db.dao.services.JavaScriptFunctionService;
import usergui.db.dao.services.JedisUserPreferenceService;
import usergui.db.dao.services.ObjectAttributeService;
import usergui.db.dao.services.UserAttributeService;
import usergui.db.dao.services.UserPreferencesService;
import usergui.model.Attribute;
import usergui.model.JavaScriptFunction;
import usergui.model.UserPreference;
import usergui.utils.PojoGenerator;
import usergui.view.UserPreferencesPanel;
import usergui.view.dialogs.AddUserPreferenceDialog;
import usergui.view.dialogs.SearchUserPreferenceDialog;

public class UserPreferencesPanelController {

	private UserPreferencesPanel preferencesPanel;

	private AddUserPreferenceDialog addPreferenceDialog;
	private SearchUserPreferenceDialog searchPreferenceDialog;
	private RSyntaxTextArea addDialogTextArea;
	private JTextPane addDialogValidationOutput;
	private RSyntaxTextArea userPreferencesPanelTextArea;
	private JTextPane userPreferencesPanelValidationOutput;

	private UserPreferencesService preferencesService;
	private UserAttributeService userAttributeService;
	private ClientAttributeService clientAttributeService;
	private ObjectAttributeService objectAttributeService;
	private EnviromentAttributeService enviromentAttributeService;
	private JavaScriptFunctionService jsFunctionService;
	private JedisUserPreferenceService jedisUserPreferencesService;

	private List<UserPreference> allPreferences;
	private List<Attribute> allAttributes;
	private List<JavaScriptFunction> allJsFunctions;

	private ScriptEngineManager factory;
	private ScriptEngine engine;
	private ScriptContext context;

	private String loggedUserId;
	private int selectedPreferencesTableRow;

	private int selectedPreferenceId;
	private UserPreference selectedPreference;

	public UserPreferencesPanelController(UserPreferencesPanel preferencesPanel) throws IOException {
		this.preferencesPanel = preferencesPanel;
		this.addPreferenceDialog = preferencesPanel.getAddUserPreferenceDialog();
		this.searchPreferenceDialog = preferencesPanel.getSearchUserPreferenceDialog();
		this.addDialogTextArea = addPreferenceDialog.getExpTextArea();
		this.addDialogValidationOutput = addPreferenceDialog.getValidationOutputText();
		this.userPreferencesPanelTextArea = preferencesPanel.getExpTextArea();
		this.userPreferencesPanelValidationOutput = preferencesPanel.getValidationOutputText();
		this.loggedUserId = preferencesPanel.getUserId();

		initServices();
		addListeners();
		initLists();
		initJsEngine();

		generateAutoCompletion();
		preferencesPanel.installCompletionService();
	}

	protected void initServices() {
		preferencesService = new UserPreferencesService();
		userAttributeService = new UserAttributeService();
		clientAttributeService = new ClientAttributeService();
		objectAttributeService = new ObjectAttributeService();
		enviromentAttributeService = new EnviromentAttributeService();
		jsFunctionService = new JavaScriptFunctionService();
		jedisUserPreferencesService = new JedisUserPreferenceService();
	}

	protected void addListeners() {
		preferencesPanel.addSelectedRowListener(new PreferenceSelectedListener());
		preferencesPanel.addPreferencesPanelButtonsListener(new PreferencesPanelButtonsListener());
		preferencesPanel.getAddUserPreferenceDialog().addAddPreferenceDialogListener(new AddPreferenceDialogListener());
		preferencesPanel.getSearchUserPreferenceDialog().addButtonsListener(new SearchPreferencesButtonListener());
	}

	protected void initLists() {
		allPreferences = new ArrayList<>();
		allJsFunctions = new ArrayList<>();
		allAttributes = new ArrayList<>();

		allAttributes.addAll(objectAttributeService.getAllAttributes());
		allAttributes.addAll(enviromentAttributeService.getAllAttributes());

		allJsFunctions = jsFunctionService.getAll();
	}

	protected void initJsEngine() {
		factory = new ScriptEngineManager();
		engine = factory.getEngineByName("JavaScript");
		context = engine.getContext();
		
		context = engine.getContext();
		context.setAttribute("s", this, ScriptContext.GLOBAL_SCOPE);
		context.setAttribute("e", this, ScriptContext.GLOBAL_SCOPE);
		context.setAttribute("o", this, ScriptContext.GLOBAL_SCOPE);
		context.setAttribute("f", this, ScriptContext.GLOBAL_SCOPE);

		for (JavaScriptFunction f : allJsFunctions) {
			context.setAttribute(f.getName(), f.getCode(), ScriptContext.GLOBAL_SCOPE);
		}
	}
	
	protected void generateAutoCompletion(){
		allAttributes = userAttributeService.getAllUserAttributes();
		allAttributes.addAll(objectAttributeService.getAllAttributes());
		allAttributes.addAll(enviromentAttributeService.getAllAttributes());

		allJsFunctions = jsFunctionService.getAll();

		allPreferences = preferencesService.getAll(loggedUserId);

		Map<String, String> subjectAtttributes = new HashMap<String, String>(
				userAttributeService.getAllAttributesAndTypes());

		subjectAtttributes.putAll(clientAttributeService.getAllAttributesAndTypes());

		Map<String, String> enviromentAtttributes = new HashMap<String, String>(
				enviromentAttributeService.getAllAttributesAndTypes());

		Map<String, String> objectAtttributes = new HashMap<String, String>(
				objectAttributeService.getAllAttributesAndTypes());

		allJsFunctions = jsFunctionService.getAll();

		PojoGenerator pj = new PojoGenerator();

		try {
			pj.generate(subjectAtttributes, "S");
			pj.generate(enviromentAtttributes, "E");
			pj.generate(objectAtttributes, "O");
			pj.generate(allJsFunctions, "F");

		} catch (NotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (CannotCompileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	protected void createJsFile(String fileName, RSyntaxTextArea textArea, boolean functions) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(fileName, "UTF-8");
			if (!functions) {
				writer.println(textArea.getText());
			} else {
				allJsFunctions.forEach(function -> writer.println(function.getCode()));
				writer.println(textArea.getText());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	protected void validateJsFunction(RSyntaxTextArea textArea, JTextPane validatioOutput, String file) {
		if (!textArea.getText().isEmpty()) {
			validatioOutput.setText("");
			if (file.equals("JS_Functions.js")) {
				createJsFile(file, textArea, false);
			} else {
				createJsFile(file, textArea, true);
			}

			try {

				Object result = engine.eval(new java.io.FileReader(file));

				if (result instanceof Boolean) {
					validatioOutput.setText(">> No syntax errors where found");
				} else {
					validatioOutput.setText(">> The parametric predicate is not correct");
					return;
				}

			} catch (FileNotFoundException e1) {
				System.out.println("File not found");
				return;
			} catch (ScriptException e1) {
				validatioOutput.setText(e1.getMessage());
				return;
			} catch (ClassCastException e1) {
				System.out.println("Class Cast is not a problem!");
			}
		}
	}

	public void createUserPreference(UserPreference newPreference) {
		preferencesService.createUserPreference(newPreference);

		preferencesPanel.addUserPreferenceToTable(preferencesService.getLastCreatedPreferencesId());

		allPreferences.add(newPreference);
		JOptionPane.showMessageDialog(addPreferenceDialog.getMainDialog(), "New Preference created with success");
		addPreferenceDialog.cleanTexts();
		addPreferenceDialog.dispose();
		return;
	}

	class PreferenceSelectedListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {

			JTable table = (JTable) arg0.getSource();
			selectedPreferencesTableRow = table.getSelectedRow();

			if (selectedPreferencesTableRow < 0) {
				return;
			}

			for (Attribute a : allAttributes) {
				context.setAttribute(a.getName(), a.getValue(), ScriptContext.GLOBAL_SCOPE);
			}

			allAttributes = userAttributeService.getUserAttributesById(addPreferenceDialog.getLoggedUserId());

			selectedPreferenceId = (int) table.getValueAt(selectedPreferencesTableRow, 0);

			selectedPreference = preferencesService.getPreferenceById(selectedPreferenceId);

			preferencesPanel.setUserPreferenceData(selectedPreference);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	class PreferencesPanelButtonsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			switch (e.getActionCommand()) {

			case "VALIDATE PARAMETRIC PREDICATE":
				validateJsFunction(userPreferencesPanelTextArea, userPreferencesPanelValidationOutput,
						"Parametric_Expression.js");

				break;

			case "OPEN ADD USER PREFERENCE DIALOG":
				addPreferenceDialog.installCompletionService();
				addPreferenceDialog.show();
				break;

			case "UPDATE PREFERENCE":

				String newTf = preferencesPanel.getTopicFilterExpression();
				String newExp = preferencesPanel.getParametricPredicate();

				if (!preferencesPanel.isParametricPredicateCorrect()) {
					JOptionPane.showMessageDialog(addPreferenceDialog.getMainDialog(),
							"The parametric predicate or you haven't validate it yet");
					return;
				}

				if (newTf.isEmpty() || newExp.isEmpty()) {
					JOptionPane.showMessageDialog(addPreferenceDialog.getMainDialog(), "Some data is empty");
					return;
				}

				int updateOption = JOptionPane.showConfirmDialog(preferencesPanel.getMainPanel(),
						"Do you want to update this User Preference?", "Warning", JOptionPane.YES_NO_OPTION);
				if (updateOption == 0) {

					String updatedTf = preferencesPanel.getTopicFilterExpression();
					String updatedExp = preferencesPanel.getParametricPredicate();

					preferencesService.updateByTfAndExp(updatedTf, updatedExp,
							selectedPreference.getTopicFilterExpression(), selectedPreference.getParametricPredicate());
					updatePreferencesList();
					JOptionPane.showMessageDialog(addPreferenceDialog.getMainDialog(), "User Preference updated!");
				}

				break;

			case "OPEN SEARCH PREFERENCE DIALOG":

				try {
					preferencesService.setUserPreferencesInTable(
							preferencesPanel.getSearchUserPreferenceDialog().getPreferencesResultTable());
					preferencesPanel.getSearchUserPreferenceDialog().initFilterTextField();

					searchPreferenceDialog.show();

				} catch (ArrayIndexOutOfBoundsException a) {
					JOptionPane.showMessageDialog(searchPreferenceDialog.getMainDialog(),
							"There are not User Preferences to search!");
					return;
				}
				break;

			case "DELETE PREFERENCE":

				if (selectedPreferencesTableRow == -1) {
					JOptionPane.showMessageDialog(addPreferenceDialog.getMainDialog(), "Select an Preference first");
					return;
				}

				try {

					int deleteOption = JOptionPane.showConfirmDialog(preferencesPanel.getMainPanel(),
							"Do you want to delete this User Preference?", "Delete Warning", JOptionPane.YES_NO_OPTION);
					if (deleteOption == 0) {

						preferencesPanel.deletePreferenceFromTable(selectedPreferencesTableRow);
						preferencesService.deleteById(selectedPreferenceId);
						updatePreferencesList();
						JOptionPane.showMessageDialog(addPreferenceDialog.getMainDialog(),
								"User Preference deleted with Success");
						preferencesPanel.cleanTexts();
					}

				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(preferencesPanel.getMainPanel(),
							"There are not User Preferences to delete");
				}
				break;

			case "EXPORT PREFERENCES TO REDIS":
				int exportOption = JOptionPane.showConfirmDialog(preferencesPanel.getMainPanel(),
						"Do you want to export all the policies to Redis?", "Warning", JOptionPane.YES_NO_OPTION);
				if (exportOption == 0) {
					jedisUserPreferencesService.savePreferences(allPreferences);
					JOptionPane.showMessageDialog(preferencesPanel.getMainPanel(),
							"User Preferences exported Successfully");
				}
				break;
			}
		}
	}

	public void updatePreferencesList() {
		allPreferences.clear();
		allPreferences = preferencesService.getAll(loggedUserId);
	}

	class PreferencesPanelNavigationsButtonsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {

			case "FIRST":
				System.out.println("first..");
				break;

			case "PREVIOUS":
				System.out.println("previous..");
				break;

			case "NEXT":
				System.out.println("next..");
				break;

			case "LAST":
				System.out.println("Last..");
				break;
			}
		}
	}

	class AddPreferenceDialogListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {

			case "VALIDATE FUNCTION":
				validateJsFunction(addDialogTextArea, addDialogValidationOutput, "Parametric_Expression.js");
				break;

			case "CREATE PREFERENCE":
				if (addPreferenceDialog.isInputDataEmpty()) {
					JOptionPane.showMessageDialog(addPreferenceDialog.getMainDialog(), "     Some fields are empty");
					return;
				}

				if (!addPreferenceDialog.isParametricPredicateCorrect()) {
					JOptionPane.showMessageDialog(addPreferenceDialog.getMainDialog(),
							"The Parametric Predicate is not correct, try again");
					return;
				}
				UserPreference newPreference = addPreferenceDialog.createUserPreference();
				allPreferences.add(newPreference);
				createUserPreference(newPreference);
				break;

			case "CLOSE ADD PREFERENCE DIALOG":
				addPreferenceDialog.dispose();
				break;
			}
		}
	}

	class SearchPreferencesButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {

			case "CLOSE DIALOG":
				preferencesPanel.getSearchUserPreferenceDialog().getMainDialog().dispose();
				break;
			}
		}
	}
}