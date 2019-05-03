package usergui.controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import usergui.db.dao.services.UserService;
import usergui.model.User;
import usergui.view.LoginFrame;
import usergui.view.MainFrame;

public class LoginFrameController {

	private JFrame loginFrame;
	private LoginFrame paneLogin;

	private MainFrame mainFrame;

	private UserService userService = new UserService();

	public LoginFrameController(LoginFrame loginFrame, MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.loginFrame = loginFrame.getMainFrame();
		this.paneLogin = loginFrame;
		loginFrame.addLoginListener(new LoginListener());
	}

	class LoginListener implements ActionListener {
		String id;
		String pswd;

		@Override
		public void actionPerformed(ActionEvent e) {

			id = paneLogin.getUserIDInput();
			pswd = paneLogin.getPasswordInput();

			switch (e.getActionCommand()) {
			case "Login":

				if (id.isEmpty() || pswd.isEmpty()) {
					JOptionPane.showMessageDialog(loginFrame, "Insert your Id and Password ", "Login Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (!userService.checkLoginCredentials(id, pswd)) {
					JOptionPane.showMessageDialog(loginFrame, "Wrong ID/Password, or the user is an Administrator ",
							"Login Error", JOptionPane.ERROR_MESSAGE);
					return;
				} else {

					loginFrame.dispose();
					JOptionPane.showMessageDialog(loginFrame, "         Login Successful");

					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								User user = userService.getUserById(id);
								mainFrame.getProfilePanel().setUserData(user);
								mainFrame.getUserPreferencesPanel().addModel(user.getId());
								mainFrame.getUserPreferencesPanel().getAddUserPreferenceDialog()
										.setUserId(user.getId());
								mainFrame.getMainFrame().setVisible(true);

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
				break;

			case "Cancel":
				loginFrame.dispose();
				break;
			}
		}
	}
}