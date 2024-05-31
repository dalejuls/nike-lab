package FinalLabAct;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;

public class ListofRecordsWindow implements ActionListener {
    static DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Name", "Birthday", "Age"}, 0);
    static JTable userDataTable = new JTable(tableModel);

    static JPanel actionPanel;
    static JButton addButton;
    static JButton removeButton;
    static JButton exportButton;

    ListofRecordsWindow() {

        JFrame mainWindow = new JFrame("Logged in");
        mainWindow.setSize(800, 700);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLayout(new BorderLayout());

        userDataTable.setBorder(BorderFactory.createLineBorder(Color.black));
        userDataTable.setPreferredScrollableViewportSize(new Dimension(750, 400));
        userDataTable.setFillsViewportHeight(true);
        mainWindow.add(new JScrollPane(userDataTable), BorderLayout.NORTH);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel sortByLabel = new JLabel("Sort by:");
        sortByLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(sortByLabel, gbc);

        String[] sortingOptions = {"Name", "Age", "Birthday"};
        JComboBox<String> sortingComboBox = new JComboBox<>(sortingOptions);
        gbc.gridx = 1;
        controlPanel.add(sortingComboBox, gbc);

        JRadioButton ascendRadioButton = new JRadioButton("Ascending");
        ascendRadioButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        gbc.gridx = 2;
        controlPanel.add(ascendRadioButton, gbc);

        JRadioButton descendRadioButton = new JRadioButton("Descending");
        descendRadioButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        gbc.gridx = 3;
        controlPanel.add(descendRadioButton, gbc);

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(ascendRadioButton);
        radioGroup.add(descendRadioButton);

        JButton sortButton = new JButton("Sort");
        gbc.gridx = 4;
        controlPanel.add(sortButton, gbc);

        mainWindow.add(controlPanel, BorderLayout.CENTER);

        actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        addButton = new JButton("Add a record");
        removeButton = new JButton("Remove a record");
        exportButton = new JButton("Export to CSV");

        actionPanel.add(addButton);
        actionPanel.add(removeButton);
        actionPanel.add(exportButton);

        mainWindow.add(actionPanel, BorderLayout.SOUTH);

        mainWindow.setLocationRelativeTo(null);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);

        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        exportButton.addActionListener(this);
        sortButton.addActionListener(e -> {
            sortTable(sortingComboBox.getSelectedItem().toString(), ascendRadioButton.isSelected());
        });
    }

    private void sortTable(String sortBy, boolean ascending) {
        Comparator<Object> comparator = null;

        switch (sortBy) {
            case "Name":
                comparator = Comparator.comparing(o -> o.toString(), Comparator.naturalOrder());
                break;
            case "Age":
                comparator = Comparator.comparing(o -> Integer.parseInt(o.toString()), Comparator.reverseOrder());
                break;
            case "Birthday":
                comparator = Comparator.comparing(o -> o.toString(), Comparator.naturalOrder());
                break;
        }

        if (comparator != null) {
            tableModel.getDataVector().sort(comparator);
            if (!ascending) {
                java.util.Collections.reverse(tableModel.getDataVector());
            }
            userDataTable.repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            new AddRecordWindow();
        } else if (e.getSource() == removeButton) {
            new RemoveRecordWindow();
        } else if (e.getSource() == exportButton) {
            CsvExportWindow.exportToCSV();
        }
    }
}