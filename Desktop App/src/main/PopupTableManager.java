package main;

import javax.swing.*;
import java.util.List;

public class PopupTableManager {

    public static void showPopupTable(String userId,String userName,List<CheckInOutDetails> detailsList) {
        
        String title = "User Details: " + userName + " (ID: " + userId + ")";

        JDialog popupDialog = new JDialog();
        popupDialog.setTitle(title);
        Object[][] data = new Object[detailsList.size()][3];
        for (int i = 0; i < detailsList.size(); i++) {
            CheckInOutDetails details = detailsList.get(i);
            data[i][0] = details.getDay();
            data[i][1] = details.getCheckIn();
            data[i][2] = details.getCheckOut();
        }

        String[] columnNames = {"Day", "Check-In", "Check-Out"};

        JTable detailsTable = new JTable(data, columnNames);

        JScrollPane scrollPane = new JScrollPane(detailsTable);
        popupDialog.add(scrollPane);

        popupDialog.setSize(400, 300); 
        popupDialog.setLocationRelativeTo(null); 
        popupDialog.setVisible(true);
    }

    
   

  
}

