package usergui.db.dao.services;

import java.util.HashMap;
import java.util.List;

import usergui.db.dao.impl.ClientAttributeDaoImpl;
import usergui.model.Attribute;


public class ClientAttributeService {

	private final ClientAttributeDaoImpl clientAttributeDaoImpl = new ClientAttributeDaoImpl();

	public void createClientAttribute(Attribute attribute) {
		clientAttributeDaoImpl.createAttribute(attribute);
	}

	public List<Attribute> getClientAttributesById(String selectedClientId) {
		return clientAttributeDaoImpl.getAttributesById(selectedClientId);
	}

	public List<Attribute> getAllClientAttributes() {
		return clientAttributeDaoImpl.getAllAttributes();
	}

	public void updateClientAttribute(Attribute attribute, String oldName) {
		clientAttributeDaoImpl.updateAttribute(attribute, oldName);
	}

	public void updateAttributesValuesByClientId(List<Attribute> attributes, String userId) {
		clientAttributeDaoImpl.updateAttributesValuesById(attributes, userId);
	}

	public void deleteClientAttribute(String attributeName) {
		clientAttributeDaoImpl.deleteAttribute(attributeName);
	}

	public HashMap<String, String> getAllAttributesAndTypes() {
		return clientAttributeDaoImpl.getAllAttributesAndTypes();
	}
}