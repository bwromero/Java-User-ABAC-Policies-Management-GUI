package usergui.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JTable;

import usergui.db.ConnectToDatabase;
import usergui.db.dao.UserDao;
import usergui.model.Attribute;
import usergui.model.User;

public class UserDaoImpl implements UserDao {

	private final Connection conn = ConnectToDatabase.createConnection();
	private final String SQL_GET_USER_BY_ID = "SELECT * FROM users WHERE Id=?";
	private final String CHECK_LOGIN_CREDENTIALS = "SELECT * FROM users WHERE id=? AND password=?";
	private final String SQL_UPDATE_PASSWORD = "UPDATE users SET password=? WHERE Id=?";

	@Override
	public User getUserById(String userId) {
		User user = new User();
		try (PreparedStatement pstmt = conn.prepareStatement(SQL_GET_USER_BY_ID)) {
			pstmt.setString(1, userId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					user.setId(rs.getString(1));
					user.setName(rs.getString(2));
					user.setSurname(rs.getString(3));
					user.setPassword(rs.getString(4));
					user.setAdmin(rs.getBoolean(5));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean checkLoginCredentials(String userId, String password) {
		try (PreparedStatement pstmt = conn.prepareStatement(CHECK_LOGIN_CREDENTIALS)) {
			pstmt.setString(1, userId);
			pstmt.setString(2, password);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					boolean isAdmin = rs.getBoolean(5);
					if (!isAdmin) {
						return true;
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public void updatePassword(String userId, String password) {
		try (PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE_PASSWORD)) {
			pstmt.setString(1, password);
			pstmt.setString(2, userId);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void createUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUser(User user, String oldId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAllUsersInTable(JTable table) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFoundUsersInTable(JTable table, User user, List<Attribute> attributes, String operator) {
		// TODO Auto-generated method stub
		
	}
}