package usergui.db.dao.services;

import java.util.List;

import usergui.db.dao.impl.JedisUserPreferencesDaoImpl;
import usergui.model.UserPreference;


public class JedisUserPreferenceService {

	private final JedisUserPreferencesDaoImpl dao = new JedisUserPreferencesDaoImpl();

	public void savePreferences(List<UserPreference> preferences) {
		dao.savePreferences(preferences);
	}
}