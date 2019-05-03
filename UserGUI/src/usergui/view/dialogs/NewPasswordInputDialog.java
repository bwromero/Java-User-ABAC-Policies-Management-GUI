package usergui.view.dialogs;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import net.miginfocom.swing.MigLayout;

public class NewPasswordInputDialog {

	private JDialog mainDialog;
	private JPanel mainPanel;

	private JLabel newPasswordLbl;
	private JPasswordField passwordField;
	private JButton cancelButton;
	private JButton okButton;

	public NewPasswordInputDialog() {
		initComponents();
	}

	protected void initComponents() {
		newPasswordLbl = new JLabel("New Password:");
		passwordField = new JPasswordField(20);
		initButtons();
		initMainPanel();
		initMainDialog();
		setActionCommands();
	}

	protected void initButtons() {
		cancelButton = new JButton("Cancel");
		okButton = new JButton("OK");
	}

	protected void initMainPanel() {
		mainPanel = new JPanel(new MigLayout("", "[]", "[][]20[]"));
		mainPanel.add(newPasswordLbl, "cell 0 0");
		mainPanel.add(passwordField, "cell 0 1,grow");
		mainPanel.add(cancelButton, "cell 0 2,right");
		mainPanel.add(okButton, "cell 0 2,right");
	}

	protected void initMainDialog() {
		mainDialog = new JDialog();
		mainDialog.setTitle("Modify Password");
		mainDialog.setBounds(550, 215, 180, 130);
		mainDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainDialog.setModal(true);
		mainDialog.setResizable(false);
		mainDialog.add(mainPanel);
	}

	public void show() {
		mainDialog.setVisible(true);
	}

	public boolean isEmptyPasswordField() {
		if (passwordField.getPassword().length == 0) {
			return true;
		}
		return false;
	}

	public boolean isNewPasswordEqualTo(String oldPassword) {
		if (String.valueOf(passwordField.getPassword()).trim().equals(oldPassword.trim())) {
			return true;
		}
		return false;
	}

	public void addButtonsListeners(ActionListener e) {
		cancelButton.addActionListener(e);
		okButton.addActionListener(e);
	}

	public void setActionCommands() {
		cancelButton.setActionCommand("CLOSE");
		okButton.setActionCommand("UPDATE PASSWORD");
	}

	public void dispose() {
		mainDialog.dispose();
	}

	public void clear() {
		passwordField.setText("");
	}

	public String getNewPassword() {
		return String.copyValueOf(passwordField.getPassword()).trim();
	}

	public JDialog getMainDialog() {
		return this.mainDialog;
	}
}