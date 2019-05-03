package usergui.model;

import java.util.HashMap;
import java.util.Map;

public class UserPreference {

	private Integer Id;
	private String userId;
	private String topicFilterExpression;
	private String parametricPredicate;

	public UserPreference() {

	}

	public String createRedisSetKey() {
		return String.valueOf(userId);
	}

	public String createRedisHashSetKey() {
		return userId + ":user_preference:";
	}

	public Map<String, String> createRedisHashSetValues() {
		Map<String, String> policyInf = new HashMap<String, String>();
		policyInf.put("tf", topicFilterExpression);
		policyInf.put("exp", parametricPredicate);
		return policyInf;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTopicFilterExpression() {
		return topicFilterExpression;
	}

	public void setTopicFilterExpression(String topicFilterExpression) {
		this.topicFilterExpression = topicFilterExpression;
	}

	public String getParametricPredicate() {
		return parametricPredicate;
	}

	public void setParametricPredicate(String parametricPredicate) {
		this.parametricPredicate = parametricPredicate;
	}

	@Override
	public String toString() {
		return "UserPreference [Id=" + Id + ", userId=" + userId + ", topicFilterExpression=" + topicFilterExpression
				+ ", parametricPredicate=" + parametricPredicate + "]";
	}
}