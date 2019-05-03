package usergui.db.dao.services;

import java.util.HashMap;
import java.util.List;

import usergui.db.dao.impl.UserAttributeDaoImpl;
import usergui.model.Attribute;

public class UserAttributeService {

	private final UserAttributeDaoImpl userAttributeDaoImpl = new UserAttributeDaoImpl();

	public void createUserAttribute(Attribute attribute) {
		userAttributeDaoImpl.createAttribute(attribute);
	}

	public List<Attribute> getUserAttributesById(String string) {
		return userAttributeDaoImpl.getAttributesById(string);
	}

	public List<Attribute> getAllUserAttributes() {
		return userAttributeDaoImpl.getAllAttributes();
	}

	public void updateUserAttribute(Attribute attribute, String oldName) {
		userAttributeDaoImpl.updateAttribute(attribute, oldName);
	}

	public void updateAttributesValuesByUserId(List<Attribute> attributes, String userId) {
		userAttributeDaoImpl.updateAttributesValuesById(attributes, userId);
	}

	public void deleteUserAttribute(String attributeName) {
		userAttributeDaoImpl.deleteAttribute(attributeName);
	}

	public HashMap<String, String> getAllAttributesAndTypes() {
		return userAttributeDaoImpl.getAllAttributesAndTypes();
	}
}
