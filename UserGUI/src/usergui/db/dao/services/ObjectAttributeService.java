package usergui.db.dao.services;

import java.util.HashMap;
import java.util.List;

import usergui.db.dao.impl.ObjectAttributeDaoImpl;
import usergui.model.Attribute;

public class ObjectAttributeService {

	private final ObjectAttributeDaoImpl objectAttributeDaoImpl = new ObjectAttributeDaoImpl();

	public void createAttribute(Attribute attribute) {
		objectAttributeDaoImpl.createAttribute(attribute);
	}

	public List<Attribute> getAllAttributes() {
		return objectAttributeDaoImpl.getAllAttributes();
	}

	public void updateAttribute(Attribute attribute, String oldName) {
		objectAttributeDaoImpl.updateAttribute(attribute, oldName);
	}

	public void deleteAttribute(String attributeName) {
		objectAttributeDaoImpl.deleteAttribute(attributeName);
	}

	public HashMap<String, String> getAllAttributesAndTypes() {
		return objectAttributeDaoImpl.getAllAttributesAndTypes();
	}
}