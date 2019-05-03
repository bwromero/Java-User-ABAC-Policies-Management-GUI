package usergui.db.dao;

import java.util.List;

import usergui.model.UserPreference;

public interface JedisUserPreferencesDao {

	public void savePreferences(List<UserPreference> policies);
	
	
}
