package usergui.db.dao;

import java.util.List;

import javax.swing.JTable;

import usergui.model.UserPreference;

public interface UserPreferenceDao {

	public void create(UserPreference preference);

	public List<UserPreference> getAll(String id);
	
	public UserPreference getPreferenceById(int id);

	public void setPreferencesInTable(JTable table);

	public void updateByTfAndExp(String tf, String exp, String oldTf, String oldExp);

	public void deleteById(int id);
	
	public int getLastCreatedPreferencesId();
}