package FinalLabAct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AddRecordWindow {
    private JFrame window;
    private JTextField nameField;
    private JSpinner monthSpinner;
    private JSpinner daySpinner;
    private JSpinner yearSpinner;

    AddRecordWindow() {
        window = new JFrame("Add a Record");
        window.setSize(350, 175);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(new FlowLayout());

        JLabel nameLabel = new JLabel("Name:");
        window.add(nameLabel);

        nameField = new JTextField(20);
        window.add(nameField);

        JLabel dateLabel = new JLabel("Date (mm/dd/yyyy):");
        window.add(dateLabel);

        SpinnerModel monthModel = new SpinnerNumberModel(1, 1, 12, 1);
        monthSpinner = new JSpinner(monthModel);
        window.add(monthSpinner);

        SpinnerModel dayModel = new SpinnerNumberModel(1, 1, 31, 1);
        daySpinner = new JSpinner(dayModel);
        window.add(daySpinner);

        SpinnerModel yearModel = new SpinnerNumberModel(2000, 1900, 2100, 1);
        yearSpinner = new JSpinner(yearModel);
        window.add(yearSpinner);

        JButton saveAndGoBackButton = new JButton("Save and Go Back");
        saveAndGoBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveRecord();
                    window.dispose();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(window, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        window.add(saveAndGoBackButton);

        JButton saveAndAddAnotherButton = new JButton("Save and Add Another");
        saveAndAddAnotherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveRecord();
                    nameField.setText("");
                    monthSpinner.setValue(1);
                    daySpinner.setValue(1);
                    yearSpinner.setValue(2000);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(window, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        window.add(saveAndAddAnotherButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
            }
        });
        window.add(backButton);

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    private void saveRecord() {
        String name = nameField.getText();
        int month = (int) monthSpinner.getValue();
        int day = (int) daySpinner.getValue();
        int year = (int) yearSpinner.getValue();

        validateDate(year, month, day);

        LocalDate birthdate = LocalDate.of(year, month, day);

        if (birthdate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birthdate cannot be in the future");
        }

        int age = calculateAge(birthdate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);
        ListofRecordsWindow.tableModel.addRow(new Object[]{name, birthdate.format(formatter), age});
    }

    private void validateDate(int year, int month, int day) {
        try {
            LocalDate date = LocalDate.of(year, month, day);
            if (date.getDayOfMonth() != day) {
                throw new IllegalArgumentException("Invalid date. Please enter a valid date in MM/dd/yyyy format.");
            }
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid date. Please enter a valid date in MM/dd/yyyy format.");
        }
    }

    private int calculateAge(LocalDate birthday) {
        LocalDate today = LocalDate.now();
        Period period = Period.between(birthday, today);
        return period.getYears();
    }
}