package usergui.db.dao;

import java.util.List;

import javax.swing.JTable;

import usergui.model.Attribute;
import usergui.model.User;

public interface UserDao {

	public void createUser(User user);

	public User getUserById(String userId);

	public List<User> getAllUsers();

	public void updateUser(User user, String oldId);

	public void deleteUser(String userId);

	public void setAllUsersInTable(JTable table);

	public void setFoundUsersInTable(JTable table, User user, List<Attribute> attributes, String operator);

	public boolean checkLoginCredentials(String id, String password);

	public void updatePassword(String userId, String password);
}