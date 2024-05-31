package FinalLabAct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveRecordWindow {
    private JFrame window;
    private JTextField tf1;

    RemoveRecordWindow() {
        window = new JFrame("Remove a Record");
        window.setSize(300, 200);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(new FlowLayout());

        JLabel nameLabel = new JLabel("Name:");
        window.add(nameLabel);

        tf1 = new JTextField(20);
        window.add(tf1);

        JButton b1 = new JButton("Remove and Go Back");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    removeRecord();
                    window.dispose();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(window, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        window.add(b1);

        JButton b2 = new JButton("Remove Another");
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    removeRecord();
                    tf1.setText("");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(window, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        window.add(b2);

        JButton b3 = new JButton("Go Back");
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
            }
        });
        window.add(b3);

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    private void removeRecord() {
        String name = tf1.getText();
        boolean found = false;

        for (int i = 0; i < ListofRecordsWindow.tableModel.getRowCount(); i++) {
            if (ListofRecordsWindow.tableModel.getValueAt(i, 0).equals(name)) {
                ListofRecordsWindow.tableModel.removeRow(i);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Name not found in the record list.");
        }
    }
}