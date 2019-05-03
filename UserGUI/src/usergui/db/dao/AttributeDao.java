package usergui.db.dao;

import java.util.HashMap;
import java.util.List;

import usergui.model.Attribute;

public interface AttributeDao {

	public void createAttribute(Attribute attribute);
	
	public void createAttributes(List<Attribute> attributes);

	public List<Attribute> getAttributesById(String id);

	public List<Attribute> getAllAttributes();

	public void updateAttribute(Attribute attribute, String oldName);

	public void updateAttributesValuesById(List<Attribute> attributes, String id);

	public void deleteAttribute(String attributeName);
	
	public HashMap<String, String> getAllAttributesAndTypes();

}
