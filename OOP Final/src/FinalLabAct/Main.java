//Contributions
//Nisay : loggedIn class / addRecordWindow class
//Julian : login Class / loggedIn class / addRecordWindow class / CSV class
//Bautista : loggedIn class / removeRecordWindow class

package FinalLabAct;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your windows username:"); // (C:\\Users\\USERNAME\\)
        String windowsUsername = scanner.nextLine();

        LoginWindow loginWindow = new LoginWindow();
        loginWindow.setWindowsUsername(windowsUsername);
        loginWindow.credentialChecker();
    }
}