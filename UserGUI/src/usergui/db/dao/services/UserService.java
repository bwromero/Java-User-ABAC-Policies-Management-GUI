package usergui.db.dao.services;

import java.util.List;

import javax.swing.JTable;

import usergui.db.dao.impl.UserDaoImpl;
import usergui.model.Attribute;
import usergui.model.User;

public class UserService {

	private final UserDaoImpl userDaoImpl = new UserDaoImpl();

	public void createUser(User user) {
		userDaoImpl.createUser(user);
	}

	public User getUserById(String userId) {
		return userDaoImpl.getUserById(userId);
	}

	public List<User> getAllUsers() {
		return userDaoImpl.getAllUsers();
	}

	public void updateUser(User user, String string) {
		userDaoImpl.updateUser(user, string);
	}

	public void deleteUser(String userId) {
		userDaoImpl.deleteUser(userId);
	}

	public void setAllUsersInTable(JTable table) {
		userDaoImpl.setAllUsersInTable(table);
	}

	public void setFoundUsersInTable(JTable table, User user, List<Attribute> attributes, String operator) {
		userDaoImpl.setFoundUsersInTable(table, user, attributes, operator);
	}

	public boolean checkLoginCredentials(String userId, String password) {
		return userDaoImpl.checkLoginCredentials(userId, password);
	}
	
	public void updatePassword(String userId, String password) {
		userDaoImpl.updatePassword(userId, password);
	}
}