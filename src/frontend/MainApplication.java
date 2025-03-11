package frontend;

import javax.swing.*;
import java.awt.*;
import backend.*;
import java.util.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        JFrame managerFrame = new JFrame("Manager Page");
        managerFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
					Database.updateEverything();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
                managerFrame.dispose();
                System.exit(0);
            }
        });
        managerFrame.setSize(600, 400);
        managerFrame.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, Manager " + username + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        managerFrame.add(welcomeLabel, BorderLayout.NORTH);

        JPanel actionsPanel = ManagerFlow.showManagerActions(username);
        managerFrame.add(actionsPanel, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 14));
        logoutButton.addActionListener(e -> {
            managerFrame.dispose();
            LoginRegisterScreen loginRegister = new LoginRegisterScreen();
            loginRegister.startGUI();
        });
        managerFrame.add(logoutButton, BorderLayout.SOUTH);

        managerFrame.setVisible(true);
    }

    public static void openSuperManagerPage(String username) {
        JFrame superManagerFrame = new JFrame("Super Manager Page");
        superManagerFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Database.updateEverything();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                superManagerFrame.dispose();
                System.exit(0);
            }
        });
        superManagerFrame.setSize(800, 600);
        superManagerFrame.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, Super Manager " + username + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        superManagerFrame.add(welcomeLabel, BorderLayout.NORTH);
        
        JPanel actionsPanel = SuperManagerFlow.showSuperManagerActions(username);
        superManagerFrame.add(actionsPanel, BorderLayout.CENTER);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 14));
        logoutButton.addActionListener(e -> {
            superManagerFrame.dispose();
            LoginRegisterScreen loginRegister = new LoginRegisterScreen();
            loginRegister.startGUI();
        });
        superManagerFrame.add(logoutButton, BorderLayout.SOUTH);
        
        superManagerFrame.setVisible(true);
    }
}