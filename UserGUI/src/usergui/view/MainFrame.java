package usergui.view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainFrame {

	private JFrame mainFrame;

	private JTabbedPane tabbedPaneAdmin;

	private JsFunctionsPanel jsFunctionsPanel;
	private ProfilePanel profilePanel;
	private UserPreferencesPanel preferencesPanel;

	public MainFrame(JsFunctionsPanel p1, ProfilePanel p2, UserPreferencesPanel p3) {
		this.jsFunctionsPanel = p1;
		this.profilePanel = p2;
		this.preferencesPanel = p3;
		tabbedPaneAdmin = new JTabbedPane();
		initMainFrame();
	}

	protected void initMainFrame() {
		mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setBounds(400, 150, 470, 400);
		mainFrame.setTitle("UserGUI");
		mainFrame.setVisible(false);
		mainFrame.setResizable(false);
		addTabs();
		mainFrame.add(tabbedPaneAdmin);
	}

	protected void addTabs() {
		tabbedPaneAdmin.addTab("User Preferences", preferencesPanel.getMainPanel());
		tabbedPaneAdmin.addTab("JavaScript Functions", jsFunctionsPanel.getMainPanel());
		tabbedPaneAdmin.addTab("Profile", profilePanel.getMainPanel());
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public ProfilePanel getProfilePanel() {
		return this.profilePanel;
	}

	public UserPreferencesPanel getUserPreferencesPanel() {
		return this.preferencesPanel;
	}
}