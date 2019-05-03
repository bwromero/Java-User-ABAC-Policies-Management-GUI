package usergui.db.dao.impl;

import java.util.List;

import redis.clients.jedis.Jedis;
import usergui.db.dao.JedisUserPreferencesDao;
import usergui.model.UserPreference;

public class JedisUserPreferencesDaoImpl implements JedisUserPreferencesDao {

	Jedis jedis = new Jedis("127.0.0.1", 6379);

	@Override
	public void savePreferences(List<UserPreference> preferences) {

		int listIndex = 1;
		int policyNum = 1;

		for (UserPreference p : preferences) {
			if (listIndex == preferences.size()) {
				break;
			}
			String hashSetKey = p.createRedisHashSetKey() + policyNum++;

			jedis.hmset(hashSetKey, p.createRedisHashSetValues());
			jedis.sadd(p.createRedisSetKey(), hashSetKey);

			policyNum = (!(String.valueOf(p.getUserId())
					.equals(String.valueOf(preferences.get(listIndex).getUserId())))) ? 1 : policyNum;

			listIndex++;
		}
	}
}