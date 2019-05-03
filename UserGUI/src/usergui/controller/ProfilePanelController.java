package usergui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import usergui.db.dao.services.UserService;
import usergui.view.ProfilePanel;
import usergui.view.dialogs.NewPasswordInputDialog;

public class ProfilePanelController {

	private ProfilePanel profilePanel;
	private NewPasswordInputDialog newPasswordDialog;

	private UserService userService = new UserService();

	public ProfilePanelController(ProfilePanel profilePanel) {
		this.profilePanel = profilePanel;
		this.newPasswordDialog = profilePanel.getNewPasswordProfileInputDialog();
		addListeners();
	}

	protected void addListeners() {
		profilePanel.addProfilePanelButtonsListener(new ProfilePanelButtonsListener());
		newPasswordDialog.addButtonsListeners(new NewPasswordDialogButtonsListeners());
	}

	class ProfilePanelButtonsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			switch (e.getActionCommand()) {
			case "MODIFY PASSWORD":
				newPasswordDialog.show();
				break;
			}
		}
	}

	class NewPasswordDialogButtonsListeners implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {

			case "CLOSE":
				closeNewPasswordDialog();
				break;

			case "UPDATE PASSWORD":

				if (newPasswordDialog.isEmptyPasswordField()) {
					JOptionPane.showMessageDialog(newPasswordDialog.getMainDialog(), "The password can't be empty ",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (newPasswordDialog.getNewPassword().length() < 8) {
					JOptionPane.showMessageDialog(newPasswordDialog.getMainDialog(),
							"The password has to be at least 7 characters long ", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (newPasswordDialog.isNewPasswordEqualTo(profilePanel.getPassword())) {
					System.out.println("new pswd is equal: "
							+ !newPasswordDialog.isNewPasswordEqualTo(profilePanel.getPassword()));
					JOptionPane.showMessageDialog(newPasswordDialog.getMainDialog(),
							"The new password can't be the same as the old one ", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				String newPswd = newPasswordDialog.getNewPassword();

				userService.updatePassword(profilePanel.getUserId(), newPswd);

				JOptionPane.showMessageDialog(newPasswordDialog.getMainDialog(),
						"The Password has been modified with success!");
				profilePanel.updateUserPassword(newPswd);
				closeNewPasswordDialog();
				break;
			}
		}
	}

	public void closeNewPasswordDialog() {
		newPasswordDialog.clear();
		newPasswordDialog.dispose();
	}
}