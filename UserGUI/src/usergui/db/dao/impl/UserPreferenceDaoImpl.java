package usergui.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import net.proteanit.sql.DbUtils;
import usergui.db.ConnectToDatabase;
import usergui.db.dao.UserPreferenceDao;
import usergui.model.UserPreference;

public class UserPreferenceDaoImpl implements UserPreferenceDao {

	private final Connection conn = ConnectToDatabase.createConnection();
	private final String SQL_CREATE_PREFERENCE = "INSERT INTO users_preferences(user_id,topic_filter_expression,parametric_predicate) VALUES (?,?,?)";
	private final String SQL_GET_ALL_PREFERENCES = "SELECT p.id,p.user_id,p.topic_filter_expression,p.parametric_predicate FROM users_preferences p WHERE user_id=? ORDER BY id";
	private final String SQL_GET_PREFERENCES_BY_SUBJECT_ID = "SELECT * FROM users_preferences ORDER BY id";
	private final String SQL_GET_PREFERENCE_BY_ID = "SELECT * FROM users_preferences WHERE id=? ORDER BY id";
	private final String SQL_UPDATE_PREFERENCE_BY_TF_AND_EXP = "UPDATE users_preferences SET topic_filter_expression=?, parametric_predicate=? WHERE topic_filter_expression=? AND parametric_predicate=?";
	private final String SQL_DELETE_PREFERENCE_BY_TF_AND_EXP = "DELETE FROM users_preferences WHERE id=?";
	private final String SQL_GET_LAST_PREFERENCE_ID = "SELECT * FROM users_preferences ORDER BY id DESC LIMIT 1";

	@Override
	public void create(UserPreference preference) {
		try (PreparedStatement pstmt = conn.prepareStatement(SQL_CREATE_PREFERENCE, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, preference.getUserId());
			pstmt.setString(2, preference.getTopicFilterExpression());
			pstmt.setString(3, preference.getParametricPredicate());
			pstmt.executeUpdate();
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					preference.setUserId(generatedKeys.getString(1));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<UserPreference> getAll(String id) {
		List<UserPreference> allPolicies = new ArrayList<>();
		try (PreparedStatement pstmt = conn.prepareStatement(SQL_GET_ALL_PREFERENCES);) {
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				UserPreference preference = new UserPreference();
				preference.setId(rs.getInt(1));
				preference.setUserId(rs.getString(2));
				preference.setTopicFilterExpression(rs.getString(3).trim());
				preference.setParametricPredicate(rs.getString(4).trim());
				allPolicies.add(preference);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return allPolicies;
	}

	public UserPreference getPreferenceById(int id) {
		UserPreference preference = new UserPreference();
		try (PreparedStatement pstmt = conn.prepareStatement(SQL_GET_PREFERENCE_BY_ID)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				preference.setId(rs.getInt(1));
				preference.setUserId(rs.getString(2));
				preference.setTopicFilterExpression(rs.getString(3).trim());
				preference.setParametricPredicate(rs.getString(4).trim());
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return preference;
	}

	@Override
	public void updateByTfAndExp(String tf, String exp, String oldTf, String oldExp) {
		try (PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE_PREFERENCE_BY_TF_AND_EXP)) {
			pstmt.setString(1, tf);
			pstmt.setString(2, exp);
			pstmt.setString(3, oldTf);
			pstmt.setString(4, oldExp);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void deleteById(int id) {
		try (PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE_PREFERENCE_BY_TF_AND_EXP)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void setPreferencesInTable(JTable table) {
		try (PreparedStatement pstmt = conn.prepareStatement(SQL_GET_PREFERENCES_BY_SUBJECT_ID)) {
			ResultSet rs = pstmt.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public int getLastCreatedPreferencesId() {
		int i = 0;
		try (PreparedStatement pstmt = conn.prepareStatement(SQL_GET_LAST_PREFERENCE_ID)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				i = rs.getInt(1);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return i;
	}
}