package usergui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import usergui.db.dao.services.UserPreferencesService;

@SuppressWarnings("serial")
public class UserPreferencesTableModel extends DefaultTableModel {

	private UserPreferencesService userPreferencesService;

	private List<Integer> userPreferencesIds;

	@SuppressWarnings("unused")
	private String loggedUserId;

	public UserPreferencesTableModel() {
		initServices();
		addColumnNames();
	}

	protected void initServices() {
		userPreferencesService = new UserPreferencesService();
	}

	private List<Integer> getAllUserPreferencesIds(String userId) {
		List<Integer> userPreferencesIds = new ArrayList<>();

		userPreferencesService.getAll(userId).forEach(c -> userPreferencesIds.add(c.getId()));
		return userPreferencesIds;
	}

	public void addColumnNames() {
		super.addColumn("ID");
	}

	public void addData(String userId) {

		userPreferencesIds = getAllUserPreferencesIds(userId);

		if (userPreferencesIds != null) {
			for (Integer id : userPreferencesIds) {
				Object[] attributeData = { id };
				super.addRow(attributeData);
			}
		}
	}

	public void addAttributeDataToRow() {

	}

	public void updateAttributeDataInRow() {

	}

	@Override
	public boolean isCellEditable(int row, int column) {
		switch (column) {
		case 0:
			return false;
		default:
			return false;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Integer.class;
		default:
			return Integer.class;
		}
	}
}