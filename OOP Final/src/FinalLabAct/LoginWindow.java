package FinalLabAct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class LoginWindow implements ActionListener {

    private JFrame loginFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel usernameLabel, passwordLabel;
    private ArrayList<String> usernames, passwords;
    private BufferedReader reader;
    private BufferedWriter nameWriter, passWriter;
    private final int MAX_ATTEMPTS = 3;
    private int attemptCount = 0;
    private String windowsUsername;

    public LoginWindow() {
        loginFrame = new JFrame();
        loginFrame.setSize(500, 300);
        loginFrame.setVisible(true);
        loginFrame.setTitle("Login");
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setLayout(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        usernameLabel.setBounds(50, 50, 150, 30);
        loginFrame.add(usernameLabel);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        passwordLabel.setBounds(50, 100, 150, 30);
        loginFrame.add(passwordLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(200, 50, 200, 30);
        loginFrame.add(usernameField);

        passwordField = new JPasswordField(50);
        passwordField.setBounds(200, 100, 200, 30);
        loginFrame.add(passwordField);

        loginButton = new JButton("Log In");
        loginButton.setBounds(200, 150, 100, 30);
        loginFrame.add(loginButton);

        loginFrame.setResizable(false);

        loginButton.addActionListener(this);
    }

    void showError(String message) {
        JOptionPane.showMessageDialog(loginFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    public void setWindowsUsername(String windowsUsername) {
        this.windowsUsername = windowsUsername;
    }
    public void credentialChecker() throws IOException {
        usernames = new ArrayList<>();
        passwords = new ArrayList<>();

        reader = new BufferedReader(new FileReader(         "C:\\Users\\" + this.windowsUsername + "\\Desktop\\FinalLabAct\\logincredentials.txt"));
        nameWriter = new BufferedWriter(new FileWriter(     "C:\\Users\\" + this.windowsUsername + "\\Desktop\\FinalLabAct\\username.txt"));
        passWriter = new BufferedWriter(new FileWriter(     "C:\\Users\\" + this.windowsUsername + "\\Desktop\\FinalLabAct\\password.txt"));

        String line;
        int index = 0;

        while ((line = reader.readLine()) != null) {
            if (index % 2 == 0) {
                usernames.add(line);
            } else {
                passwords.add(line);
            }
            index++;
        }

        for (String name : usernames) {
            nameWriter.write(name);
            nameWriter.newLine();
        }

        for (String password : passwords) {
            passWriter.write(password);
            passWriter.newLine();
        }

        nameWriter.close();
        passWriter.close();
        reader.close();

        System.out.println("Usernames: " + usernames + " Passwords: " + passwords);

        new LoginWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String inputUsername = usernameField.getText();
            String inputPassword = new String(passwordField.getPassword());

            boolean userFound = false;
            for (int j = 0; j < usernames.size(); j++)
                if (inputUsername.equals(usernames.get(j))) {
                    userFound = true;
                    if (inputPassword.equals(passwords.get(j))) {
                        loginFrame.dispose();
                        JOptionPane.showMessageDialog(loginFrame, "Authorized Credentials", "Logged in", JOptionPane.PLAIN_MESSAGE);
                        new ListofRecordsWindow();
                        return;
                    } else {
                        showError("Wrong Password! Please try again. Max tries: 3");
                        attemptCount++;
                        passwordField.setText("");
                        if (attemptCount >= MAX_ATTEMPTS) {
                            loginButton.setEnabled(false);
                            showError("Maximum attempts reached! You have been locked out.");
                        }
                        return;
                    }
                }

            if (!userFound) {
                showError("User not found! Please try again. Max tries: 3");
                attemptCount++;
                usernameField.setText("");
                passwordField.setText("");
                if (attemptCount >= MAX_ATTEMPTS) {
                    loginButton.setEnabled(false);
                    showError("Maximum attempts reached! You have been locked out.");
                }
            }
        }
    }
}