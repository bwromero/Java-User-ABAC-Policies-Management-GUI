package usergui.controller;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.UIManager;

import usergui.view.JsFunctionsPanel;
import usergui.view.LoginFrame;
import usergui.view.MainFrame;
import usergui.view.ProfilePanel;
import usergui.view.UserPreferencesPanel;

public class MainController {

	private static LoginFrame loginFrame;
	private static MainFrame mainFrame;

	private JsFunctionsPanel jsFunctionsPanel;
	private ProfilePanel profilePanel;
	private UserPreferencesPanel userPreferencesPanel;

	private LoginFrameController loginController;
	private JavaScriptFunctionsController jsFunctionsController;
	private ProfilePanelController profileController;
	private UserPreferencesPanelController preferencesPanelController;

	public MainController() {
		initPanels();
		initFrames();
		initControllers();
	}

	protected void initPanels() {
		jsFunctionsPanel = new JsFunctionsPanel();
		profilePanel = new ProfilePanel();
		userPreferencesPanel = new UserPreferencesPanel();
	}

	protected void initFrames() {
		loginFrame = new LoginFrame();
		mainFrame = new MainFrame(jsFunctionsPanel, profilePanel, userPreferencesPanel);
	}

	protected void initControllers() {
		loginController = new LoginFrameController(loginFrame, mainFrame);
		jsFunctionsController = new JavaScriptFunctionsController(jsFunctionsPanel);
		profileController = new ProfilePanelController(profilePanel);
		try {
			preferencesPanelController = new UserPreferencesPanelController(userPreferencesPanel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public LoginFrameController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginFrameController loginController) {
		this.loginController = loginController;
	}

	public JavaScriptFunctionsController getJsFunctionsController() {
		return jsFunctionsController;
	}

	public void setJsFunctionsController(JavaScriptFunctionsController jsFunctionsController) {
		this.jsFunctionsController = jsFunctionsController;
	}

	public ProfilePanelController getProfileController() {
		return profileController;
	}

	public void setProfileController(ProfilePanelController profileController) {
		this.profileController = profileController;
	}

	public UserPreferencesPanelController getPreferencesPanelController() {
		return preferencesPanelController;
	}

	public void setPreferencesPanelController(UserPreferencesPanelController preferencesPanelController) {
		this.preferencesPanelController = preferencesPanelController;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					@SuppressWarnings("unused")
					MainController mainController = new MainController();
					loginFrame.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}