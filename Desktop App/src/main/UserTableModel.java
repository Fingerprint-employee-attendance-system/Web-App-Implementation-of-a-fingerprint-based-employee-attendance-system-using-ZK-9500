package main;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UserTableModel extends AbstractTableModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4296328687305340446L;
	private List<Employee> userList;
    private String[] columnNames = {"User ID", "User Name", "Check-In", "Check-Out"};

    public UserTableModel(List<Employee> userList) {
        this.userList = userList;
    }

    @Override
    public int getRowCount() {
        return userList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee user = userList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return user.getUserId();
            case 1:
                return user.getUserName();
            case 2:
                return user.getCheckIn();
            case 3:
                return user.getCheckOut();
            // Add more cases if needed
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    
    //call this method whenever your data changes, and the JTable will be automatically updated
    public void setUserList(List<Employee> userList) {
        this.userList = userList;
        fireTableDataChanged();
    }
}

