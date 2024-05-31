package FinalLabAct;


import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static FinalLabAct.ListofRecordsWindow.tableModel;

public class CsvExportWindow {

    public static void exportToCSV() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyyHHmmss");
        String fileName = dateFormat.format(new Date()) + ".csv";
        try (FileWriter writer = new FileWriter("C:\\Users\\USER\\Desktop\\New folder\\" + fileName + ".csv")) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    writer.append(tableModel.getValueAt(i, j).toString());
                    if (j < tableModel.getColumnCount() - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }
            writer.flush();
            JOptionPane.showMessageDialog(null, "CSV file exported successfully.");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error exporting CSV file.");
        }
    }
}
