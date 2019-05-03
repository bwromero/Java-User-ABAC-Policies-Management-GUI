package usergui.view.dialogs;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.miginfocom.swing.MigLayout;
import usergui.utils.RowFilterUtil;

public class SearchUserPreferenceDialog {

	private JDialog mainDialog;
	private JPanel mainPanel;
	private JPanel resultsTablePanel;

	private JLabel filterLabel;
	private JTextField filterInputTextField;

	private JScrollPane tableSp;
	private JTable preferencesResultTable;
	private TableRowSorter<TableModel> sorter;

	private JButton closeButton;

	public SearchUserPreferenceDialog() {
		initComponents();
	}

	protected void initComponents() {
		initButtons();
		initLabels();
		initTable();
		initMainPanel();
		initMainDialog();
	}

	protected void initLabels() {
		filterLabel = new JLabel("Filter by Topic Filter Expression:");
	}

	protected void initButtons() {
		closeButton = new JButton("Close");
		setActionCommands();
	}

	protected void setActionCommands() {
		closeButton.setActionCommand("CLOSE DIALOG");

	}

	protected void initTable() {
		preferencesResultTable = new JTable();
		tableSp = new JScrollPane(preferencesResultTable);
		filterInputTextField = new JTextField();
		preferencesResultTable.setFillsViewportHeight(true);
	}

	protected void initMainDialog() {
		mainDialog = new JDialog();
		mainDialog.setTitle("Search User Preferences");
		mainDialog.setBounds(400, 150, 400, 400);
		mainDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainDialog.setModal(true);
		mainDialog.getContentPane().add(mainPanel);
	}

	protected void initMainPanel() {
		mainPanel = new JPanel(new MigLayout("fill", "[]", "[][]"));
		mainPanel.add(createResultsTablePanel(), "cell 0 0,grow");
		mainPanel.add(closeButton, "cell 0 1,right");
	}

	protected JPanel createResultsTablePanel() {
		resultsTablePanel = new JPanel(new MigLayout("fill", "[]", "[][]"));
		resultsTablePanel.add(filterLabel, "cell 0 0");
		resultsTablePanel.add(filterInputTextField, "cell 0 0,growx");
		resultsTablePanel.add(tableSp, "cell 0 1,grow");
		return resultsTablePanel;
	}

	public void initFilterTextField() {
		resultsTablePanel.remove(filterInputTextField);
		filterInputTextField = RowFilterUtil.createRowFilter(preferencesResultTable);
		resultsTablePanel.add(filterInputTextField, "cell 0 0,growx");
		resultsTablePanel.repaint();
		resultsTablePanel.revalidate();
	}

	public void newFilter() {
		RowFilter<TableModel, Object> rf = null;
		try {
			rf = RowFilter.regexFilter(filterInputTextField.getText(), 0);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}

	public void addButtonsListener(ActionListener e) {
		closeButton.addActionListener(e);
	}

	public void show() {
		this.mainDialog.setVisible(true);
	}

	public JDialog getMainDialog() {
		return this.mainDialog;
	}

	public JTextField getFilterInputTextField() {
		return filterInputTextField;
	}

	public void setFilterInputTextField(JTextField filterInputTextField) {
		this.filterInputTextField = filterInputTextField;
	}

	public JTable getPreferencesResultTable() {
		return preferencesResultTable;
	}

	public void setPreferencesResultTable(JTable policiesResultTable) {
		this.preferencesResultTable = policiesResultTable;
	}
}