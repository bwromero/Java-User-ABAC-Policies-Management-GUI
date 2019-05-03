package usergui.db.dao.services;

import java.util.List;

import javax.swing.JTable;

import usergui.db.dao.impl.UserPreferenceDaoImpl;
import usergui.model.UserPreference;

public class UserPreferencesService {
	private final UserPreferenceDaoImpl dao = new UserPreferenceDaoImpl();

	public void createUserPreference(UserPreference policy) {
		dao.create(policy);
	}

	public List<UserPreference> getAll(String id) {
		return dao.getAll(id);
	}

	public UserPreference getPreferenceById(int id) {
		return dao.getPreferenceById(id);
	}

	public void setUserPreferencesInTable(JTable table) {
		dao.setPreferencesInTable(table);
	}

	public void updateByTfAndExp(String tf, String exp, String oldTf, String oldExp) {
		dao.updateByTfAndExp(tf, exp, oldTf, oldExp);
	}

	public void deleteById(int id) {
		dao.deleteById(id);
	}

	public int getLastCreatedPreferencesId() {
		return dao.getLastCreatedPreferencesId();
	}
}