package frontend;
import backend.*;
import javax.swing.*;
import java.awt.*;

public class LoginRegisterScreen {

    public static void startGUI() {
        JFrame frame = new JFrame("Client Portal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel titleLabel = new JLabel("Welcome! Please choose an option:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, gbc);

        gbc.gridy++;
        JButton clientLoginButton = new JButton("Client Login");
        clientLoginButton.setFont(new Font("Arial", Font.PLAIN, 14));
        clientLoginButton.addActionListener(e -> {
            frame.dispose();
            showLoginForm();
        });
        panel.add(clientLoginButton, gbc);

        gbc.gridy++;
        JButton managerLoginButton = new JButton("Manager Login");
        managerLoginButton.setFont(new Font("Arial", Font.PLAIN, 14));
        managerLoginButton.addActionListener(e -> {
            frame.dispose();
            showManagerLoginForm();
        });
        panel.add(managerLoginButton, gbc);

        gbc.gridy++;
        JButton superManagerLoginButton = new JButton("Super Manager Login");
        superManagerLoginButton.setFont(new Font("Arial", Font.PLAIN, 14));
        superManagerLoginButton.addActionListener(e -> {
            frame.dispose();
            showSuperManagerLoginForm();
        });
        panel.add(superManagerLoginButton, gbc);

        gbc.gridy++;
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 14));
        registerButton.addActionListener(e -> {
            showRegisterRoles();
            frame.dispose();
        });
        panel.add(registerButton, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void showRegisterRoles() {
        JFrame registerFrame = new JFrame("Register");
        registerFrame.setSize(400, 300);
        registerFrame.setLocationRelativeTo(null);
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel registerLabel = new JLabel("Select your role:");
        registerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        registerPanel.add(registerLabel, gbc);

        String[] roles = {"Student", "Faculty", "Non-Faculty", "Visitor"}; // Removed "Manager"
        for (String role : roles) {
            gbc.gridy++;
            JButton roleButton = new JButton(role);
            roleButton.setFont(new Font("Arial", Font.PLAIN, 14));
            roleButton.addActionListener(e -> {
                registerFrame.dispose();
                showRegisterForm(role);
            });
            registerPanel.add(roleButton, gbc);
        }

        registerFrame.add(registerPanel);
        registerFrame.setVisible(true);
    }
    
    private static void showRegisterForm(String role) {
        JFrame formFrame = new JFrame("Register - " + role);
        formFrame.setSize(400, 300);
        formFrame.setLocationRelativeTo(null);
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel formLabel = new JLabel("Enter your credentials:");
        formLabel.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(formLabel, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Email:"), gbc); // No longer checking for Manager role
        gbc.gridx = 1;
        JTextField usernameEmailField = new JTextField(15);
        formPanel.add(usernameEmailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton submitButton = new JButton("Register");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 14));
        submitButton.addActionListener(e -> {
            String usernameOrEmail = usernameEmailField.getText();
            String password = new String(passwordField.getPassword());
            if (usernameOrEmail.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(formFrame, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else { // Removed Manager role check
            	try {
            	    Client newClient = Client.registerUser(role, usernameOrEmail, password);
            	    JOptionPane.showMessageDialog(formFrame, "Successfully registered!", "Success", JOptionPane.INFORMATION_MESSAGE);
            	    formFrame.dispose(); // Close registration window

            	    // Redirect to OptionsScreen after successful registration
            	    new OptionsScreen(newClient);
            	} catch (Exception e1) {
            	    JOptionPane.showMessageDialog(formFrame, "Failed to register", "Error", JOptionPane.INFORMATION_MESSAGE);
            	}

            }
        });
        formPanel.add(submitButton, gbc);

        formFrame.add(formPanel);
        formFrame.setVisible(true);
    }
    
    private static void showLoginForm() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 400);
        loginFrame.setLocationRelativeTo(null);
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel loginLabel = new JLabel("Enter your credentials:");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridwidth = 2;
        loginPanel.add(loginLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        loginPanel.add(new JLabel("Username/Email:"), gbc);
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        loginPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        loginPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        loginPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JLabel roleLabel = new JLabel("Select your role:");
        roleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        loginPanel.add(roleLabel, gbc);

        String[] roles = {"Manager", "Student", "Faculty", "Non-Faculty", "Visitor"};
        JPanel rolePanel = new JPanel();
        rolePanel.setLayout(new FlowLayout());
        ButtonGroup roleGroup = new ButtonGroup();
        final String[] selectedRole = {""};

        for (String role : roles) {
            JRadioButton roleButton = new JRadioButton(role);
            roleButton.setFont(new Font("Arial", Font.PLAIN, 14));
            roleGroup.add(roleButton);
            rolePanel.add(roleButton);
            roleButton.addActionListener(e -> selectedRole[0] = role);
        }

        gbc.gridy++;
        loginPanel.add(rolePanel, gbc);

        gbc.gridy++;
        JButton submitButton = new JButton("Login");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 14));
        submitButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty() || selectedRole[0].isEmpty()) {
                JOptionPane.showMessageDialog(loginFrame, "Please enter all details!", "Error", JOptionPane.ERROR_MESSAGE);
            } 
            else if (!selectedRole[0].equals("Manager")) { // Ensure only clients are handled
                boolean isAuthorized = Client.authenticate(username, password);
                if (isAuthorized) {
                    loginFrame.dispose();  // Close login window
                    Client loggedInClient = Client.getClientByEmail(username);  // Fetch client instance
                    JOptionPane.showMessageDialog(loginFrame, "Logged in successfully as " + username);
                    new OptionsScreen(loggedInClient); // Redirect to OptionsScreen
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Login Failed. Please try again.");
                }
            } else {
                // Handle Manager Login (if needed)
            }
        });

        loginPanel.add(submitButton, gbc);

        loginFrame.add(loginPanel);
        loginFrame.setVisible(true);
    }


    private static void showManagerLoginForm() {
        showManagerLogin(false);
    }
    private static void showSuperManagerLoginForm(){
        showManagerLogin(true);
    }

    private static void showManagerLogin(boolean isSuperManager) {
        JFrame loginFrame = new JFrame((isSuperManager ? "Super" : "") + "Manager Login");
        loginFrame.setSize(400, 200);
        loginFrame.setLocationRelativeTo(null);
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        loginPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        loginPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        loginPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        loginPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton submitButton = new JButton("Login");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 14));
        submitButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginFrame, "Please enter all details!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean isAuthenticated = false;
                if(isSuperManager){
                    isAuthenticated = SuperManager.authenticate(username, password);
                } else {
                    isAuthenticated = Manager.authenticate(username, password);
                }

                if (isAuthenticated) {
                    loginFrame.dispose();
                    JOptionPane.showMessageDialog(loginFrame, (isSuperManager ? "Super" : "") +"Manager Login Successful!");
                    // Open Manager/Super Manager's main page
                    if(isSuperManager){
                        MainApplication.openSuperManagerPage(username);
//                    	ManagerFlow.showManagerActions(username);
                    } else{
                        MainApplication.openManagerPage(username);
//                    	ManagerFlow.showManagerActions(username);
                    }

                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Login Failed. Please try again.");
                }
            }
        });
        loginPanel.add(submitButton, gbc);

        loginFrame.add(loginPanel);
        loginFrame.setVisible(true);
    }
}