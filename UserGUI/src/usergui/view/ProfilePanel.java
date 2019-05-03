package usergui.view;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import usergui.model.User;
import usergui.view.dialogs.NewPasswordInputDialog;

public class ProfilePanel {

	private JPanel mainPanel;

	private JLabel id;
	private JLabel password;
	private JLabel name;
	private JLabel surname;
	private JLabel role;
	private JLabel userId;
	private JLabel userPassword;
	private JLabel userName;
	private JLabel userSurname;
	private JLabel userRole;

	private JButton modifyPassword;

	private NewPasswordInputDialog newPasswordInputDialog;

	private String userPswd;

	public ProfilePanel() {
		initComponents();
	}

	protected void initComponents() {
		newPasswordInputDialog = new NewPasswordInputDialog();
		initButtons();
		initLabels();
		initMainPanel();
	}

	protected void initButtons() {
		modifyPassword = new JButton("Modify Password...");
		modifyPassword.setActionCommand("MODIFY PASSWORD");
	}

	protected void initLabels() {
		id = new JLabel("ID:");
		password = new JLabel("Password:");
		name = new JLabel("Name:");
		surname = new JLabel("Surname:");
		role = new JLabel("Role:");
		setBold();
		userId = new JLabel();
		userPassword = new JLabel();
		userName = new JLabel();
		userSurname = new JLabel();
		userRole = new JLabel();
	}

	protected void initMainPanel() {
		mainPanel = new JPanel(new MigLayout("", "[]", "[][]"));
		mainPanel.add(createUserInformationPanel(), "cell 0 0");
		mainPanel.add(modifyPassword, "cell 0 1,alignx left,aligny center");
	}

	protected JPanel createUserInformationPanel() {
		JPanel panel = new JPanel(new MigLayout("", "[][]25[][]25[][]", "[][][][][]"));
		panel.setBorder(BorderFactory.createTitledBorder("User Information"));
		panel.add(id, "cell 0 0");
		panel.add(userId, "cell 1 0");
		panel.add(password, "cell 0 1");
		panel.add(userPassword, "cell 1 1");
		panel.add(name, "cell 2 0");
		panel.add(userName, "cell 3 0");
		panel.add(surname, "cell 4 0");
		panel.add(userSurname, "cell 5 0");
		panel.add(role, "cell 2 1");
		panel.add(userRole, "cell 3 1");
		return panel;
	}

	public void addProfilePanelButtonsListener(ActionListener e) {
		modifyPassword.addActionListener(e);
	}

	public void updateUserPassword(String newPassword) {
		userPassword.setText(newPassword);
	}

	public void setBold() {
		id.setFont(new Font("Tahoma", Font.BOLD, 11));
		password.setFont(new Font("Tahoma", Font.BOLD, 11));
		name.setFont(new Font("Tahoma", Font.BOLD, 11));
		surname.setFont(new Font("Tahoma", Font.BOLD, 11));
		role.setFont(new Font("Tahoma", Font.BOLD, 11));
	}

	public void setUserData(User user) {
		this.userPswd = user.getPassword();
		userId.setText(String.valueOf(user.getId()));
		userName.setText(user.getName());
		userSurname.setText(user.getSurname());
		userPassword.setText("*****");
		userRole.setText(user.getRole());
	}

	public String getPassword() {
		return this.userPswd;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public NewPasswordInputDialog getNewPasswordProfileInputDialog() {
		return newPasswordInputDialog;
	}

	public String getUserId() {
		return userId.getText();
	}
}