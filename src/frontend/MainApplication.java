package frontend;

import javax.swing.*;
import java.awt.*;
import backend.*;
import java.util.*;

public class MainApplication {

    public static void main(String[] args) {
        try {
            Database.loadEverything();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            LoginRegisterScreen loginRegister = new LoginRegisterScreen();
            loginRegister.startGUI();
        });
    }

    public static void openManagerPage(String username) {
        // Create and show the Manager's main page
        JFrame managerFrame = new JFrame("Manager Page");
        managerFrame.setSize(600, 400);
        managerFrame.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, Manager " + username + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        managerFrame.add(welcomeLabel, BorderLayout.NORTH);

        // Creating the Manager Actions Panel
        JPanel actionsPanel = ManagerFlow.showManagerActions(username);

        // Adding it to the main frame
        managerFrame.add(actionsPanel, BorderLayout.CENTER);

        managerFrame.setVisible(true);
    }


    public static void openSuperManagerPage(String username) {
        // Create and show the Super Manager's main page
        JFrame superManagerFrame = new JFrame("Super Manager Page");
        superManagerFrame.setSize(800, 600);
        superManagerFrame.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, Super Manager " + username + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        superManagerFrame.add(welcomeLabel, BorderLayout.NORTH);
        
        // Creating the Manager Actions Panel
        JPanel actionsPanel = SuperManagerFlow.showSuperManagerActions(username);

        // Add other super manager functionalities here
        superManagerFrame.add(actionsPanel, BorderLayout.CENTER);

        superManagerFrame.setVisible(true);
    }
}