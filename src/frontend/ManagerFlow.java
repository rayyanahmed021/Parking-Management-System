package frontend;
import javax.swing.*;
import java.awt.*;
//import backend.Database;

public class ManagerFlow {
	public static void showManagerActions() {
        JFrame adminFrame = new JFrame("Manager Actions");
        adminFrame.setSize(400, 300);
        adminFrame.setLocationRelativeTo(null);
        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel adminLabel = new JLabel("Manager Actions:");
        adminLabel.setFont(new Font("Arial", Font.BOLD, 16));
        adminPanel.add(adminLabel, gbc);

        gbc.gridy++;
        JButton addParkingLotButton = new JButton("Add Parking Lot");
        addParkingLotButton.setFont(new Font("Arial", Font.PLAIN, 14));
        addParkingLotButton.addActionListener(e -> addParkingLotForm());
        adminPanel.add(addParkingLotButton, gbc);

        gbc.gridy++;
        JButton updateParkingLotButton = new JButton("Update Parking Lot");
        updateParkingLotButton.setFont(new Font("Arial", Font.PLAIN, 14));
        updateParkingLotButton.addActionListener(e -> JOptionPane.showMessageDialog(adminFrame, "Update Parking Lot clicked!"));
        adminPanel.add(updateParkingLotButton, gbc);

        gbc.gridy++;
        JButton updateParkingSpaceButton = new JButton("Update Parking Space");
        updateParkingSpaceButton.setFont(new Font("Arial", Font.PLAIN, 14));
        updateParkingSpaceButton.addActionListener(e -> JOptionPane.showMessageDialog(adminFrame, "Update Parking Space clicked!"));
        adminPanel.add(updateParkingSpaceButton, gbc);

        gbc.gridy++;
        JButton validateClientButton = new JButton("Validate Client Registration");
        validateClientButton.setFont(new Font("Arial", Font.PLAIN, 14));
        validateClientButton.addActionListener(e -> JOptionPane.showMessageDialog(adminFrame, "Validate Client Registration clicked!"));
        adminPanel.add(validateClientButton, gbc);

        adminFrame.add(adminPanel);
        adminFrame.setVisible(true);
    }
	
	private static void addParkingLotForm() {
	    JFrame formFrame = new JFrame("User Details Form");
	    formFrame.setSize(400, 250);
	    formFrame.setLocationRelativeTo(null);
	    JPanel formPanel = new JPanel();
	    formPanel.setLayout(new GridBagLayout());

	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(10, 10, 10, 10);
	    gbc.gridx = 0;
	    gbc.gridy = 0;

	    // ID Field
	    formPanel.add(new JLabel("ID:"), gbc);
	    gbc.gridx = 1;
	    JTextField idField = new JTextField(15);
	    formPanel.add(idField, gbc);

	    // Name Field
	    gbc.gridx = 0;
	    gbc.gridy++;
	    formPanel.add(new JLabel("Name:"), gbc);
	    gbc.gridx = 1;
	    JTextField nameField = new JTextField(15);
	    formPanel.add(nameField, gbc);

	    // State Field
	    gbc.gridx = 0;
	    gbc.gridy++;
	    formPanel.add(new JLabel("State:"), gbc);
	    gbc.gridx = 1;
	    JTextField stateField = new JTextField(15);
	    formPanel.add(stateField, gbc);

	    // Submit Button
	    gbc.gridx = 0;
	    gbc.gridy++;
	    gbc.gridwidth = 2;
	    JButton submitButton = new JButton("Submit");
	    submitButton.setFont(new Font("Arial", Font.PLAIN, 14));
	    submitButton.addActionListener(e -> {
	        String id = idField.getText();
	        String name = nameField.getText();
	        String state = stateField.getText();

	        if (id.isEmpty() || name.isEmpty() || state.isEmpty()) {
	            JOptionPane.showMessageDialog(formFrame, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
	        } else {
	            JOptionPane.showMessageDialog(formFrame, "Parking Lot Details Submitted");
	            formFrame.dispose(); // Close the form after submission
	        }
	    });
	    formPanel.add(submitButton, gbc);

	    formFrame.add(formPanel);
	    formFrame.setVisible(true);
	}

}
