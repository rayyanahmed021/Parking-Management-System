package frontend;
import javax.swing.*;

import backend.*;

import java.awt.*;
import java.util.ArrayList;

public class SuperManagerFlow {
	public static SuperManager getSuperManagerLoggedIn(String username) {
		Database db = Database.getInstance();
	    ArrayList<Manager> managers = db.getAllManagers();
	    SuperManager managerLoggedIn = null;

	    for (Manager m : managers) {
	        if (m.getName().equals(username)) {
	            managerLoggedIn = (SuperManager) m;
	            return managerLoggedIn;
	        }
	    }
	    return managerLoggedIn;
	}
	
	public static JPanel showSuperManagerActions(String username) {
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
	    addParkingLotButton.addActionListener(e -> addParkingLotForm(username));
	    adminPanel.add(addParkingLotButton, gbc);

	    gbc.gridy++;
	    JButton updateParkingLotButton = new JButton("Update Parking Lot");
	    updateParkingLotButton.setFont(new Font("Arial", Font.PLAIN, 14));
	    updateParkingLotButton.addActionListener(e -> showParkingLotSelection());
	    adminPanel.add(updateParkingLotButton, gbc);

	    gbc.gridy++;
	    JButton updateParkingSpaceButton = new JButton("Update Parking Space");
	    updateParkingSpaceButton.setFont(new Font("Arial", Font.PLAIN, 14));
	    updateParkingSpaceButton.addActionListener(e -> showParkingSpaceLotSelection());
	    adminPanel.add(updateParkingSpaceButton, gbc);

	    gbc.gridy++;
	    JButton validateClientButton = new JButton("Validate Client Registration");
	    validateClientButton.setFont(new Font("Arial", Font.PLAIN, 14));
	    validateClientButton.addActionListener(e -> showClientSelection(username));
	    adminPanel.add(validateClientButton, gbc);
	    
	    gbc.gridy++;
	    JButton createManagerButton = new JButton("Create Manager Account");
	    createManagerButton.setFont(new Font("Arial", Font.PLAIN, 14));
	    createManagerButton.addActionListener(e -> createManagerForm(username));
	    adminPanel.add(createManagerButton, gbc);

	    return adminPanel;
	}
	
private static void showParkingLotSelection() {
	    
	    JFrame parkingFrame = new JFrame("Select a Parking Lot");
        parkingFrame.setSize(400, 400);
        parkingFrame.setLocationRelativeTo(null);

        JPanel parkingPanel = new JPanel();
        parkingPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel titleLabel = new JLabel("Select a Parking Lot:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        parkingPanel.add(titleLabel, gbc);

        Database db = Database.getInstance();
        ArrayList<ParkingLot> parkingLots = db.getAllParkingLots();

        ButtonGroup group = new ButtonGroup();
        final ParkingLot[] selectedLot = {null};

        for (ParkingLot lot : parkingLots) {
            gbc.gridy++;
            JRadioButton lotButton = new JRadioButton("Name: " + lot.getName() + " | Location: " +lot.getLocation()+ " | State: " + (lot.getState().isEnabled() ? "Enabled" : "Disabled"));
            lotButton.setFont(new Font("Arial", Font.PLAIN, 14));
            group.add(lotButton);
            parkingPanel.add(lotButton, gbc);

            lotButton.addActionListener(e -> selectedLot[0] = lot);
        }

        gbc.gridy++;
        JButton submitButton = new JButton("Select");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 14));
        submitButton.addActionListener(e -> {
            if (selectedLot[0] == null) {
                JOptionPane.showMessageDialog(parkingFrame, "Please select a parking lot!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
            	showUpdateParkingLot(selectedLot[0]);
                parkingFrame.dispose();
            }
        });

        parkingPanel.add(submitButton, gbc);
        parkingFrame.add(parkingPanel);
        parkingFrame.setVisible(true);
	}
	
private static void showParkingSpaceLotSelection() {
    
    JFrame parkingFrame = new JFrame("Select a Parking Lot");
    parkingFrame.setSize(400, 400);
    parkingFrame.setLocationRelativeTo(null);

    JPanel parkingPanel = new JPanel();
    parkingPanel.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.gridx = 0;
    gbc.gridy = 0;

    JLabel titleLabel = new JLabel("Select a Parking Lot:");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
    parkingPanel.add(titleLabel, gbc);

    Database db = Database.getInstance();
    ArrayList<ParkingLot> parkingLots = db.getAllParkingLots();

    ButtonGroup group = new ButtonGroup();
    final ParkingLot[] selectedLot = {null};

    for (ParkingLot lot : parkingLots) {
        gbc.gridy++;
        JRadioButton lotButton = new JRadioButton("Name: " + lot.getName() + " | Location: " +lot.getLocation()+ " | State: " + (lot.getState().isEnabled() ? "Enabled" : "Disabled"));
        lotButton.setFont(new Font("Arial", Font.PLAIN, 14));
        group.add(lotButton);
        parkingPanel.add(lotButton, gbc);

        lotButton.addActionListener(e -> selectedLot[0] = lot);
    }

    gbc.gridy++;
    JButton submitButton = new JButton("Select");
    submitButton.setFont(new Font("Arial", Font.PLAIN, 14));
    submitButton.addActionListener(e -> {
        if (selectedLot[0] == null) {
            JOptionPane.showMessageDialog(parkingFrame, "Please select a parking lot!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
        	showParkingSpacesSelection(selectedLot[0]);
            parkingFrame.dispose();
        }
    });

    parkingPanel.add(submitButton, gbc);
    parkingFrame.add(parkingPanel);
    parkingFrame.setVisible(true);
}


private static void showParkingSpacesSelection(ParkingLot lot) {
    JFrame frame = new JFrame("Parking Spaces in Lot " + lot.getName());
    frame.setSize(400, 400);
    frame.setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.gridx = 0;
    gbc.gridy = 0;

    JLabel titleLabel = new JLabel("Parking Spaces in Lot " + lot.getName());
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
    contentPanel.add(titleLabel, gbc);

    gbc.gridy++;
    JLabel header = new JLabel("Parking Space ID | Status");
    header.setFont(new Font("Arial", Font.BOLD, 14));
    contentPanel.add(header, gbc);

    ButtonGroup group = new ButtonGroup();
    final ParkingSpace[] selectedSpace = {null};

    JPanel scrollablePanel = new JPanel();
    scrollablePanel.setLayout(new GridBagLayout());
    
    GridBagConstraints scrollGbc = new GridBagConstraints();
    scrollGbc.insets = new Insets(5, 10, 5, 10);
    scrollGbc.gridx = 0;
    scrollGbc.gridy = 0;

    for (ParkingSpace space : lot.getParkingSpaces()) {
        JRadioButton spaceButton = new JRadioButton(
            "Parking Space " + space.getId() + " | " + (space.isEnabled() ? "Enabled" : "Disabled")
        );
        spaceButton.setFont(new Font("Arial", Font.PLAIN, 14));
        group.add(spaceButton);
        scrollablePanel.add(spaceButton, scrollGbc);
        scrollGbc.gridy++;

        spaceButton.addActionListener(e -> selectedSpace[0] = space);
    }

    JScrollPane scrollPane = new JScrollPane(scrollablePanel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setPreferredSize(new Dimension(350, 200));

    gbc.gridy++;
    contentPanel.add(scrollPane, gbc);

    gbc.gridy++;
    JButton submitButton = new JButton("Select");
    submitButton.setFont(new Font("Arial", Font.PLAIN, 14));
    submitButton.addActionListener(e -> {
        if (selectedSpace[0] == null) {
            JOptionPane.showMessageDialog(frame, "Please select a parking space!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            showUpdateParkingSpace(lot, selectedSpace[0]);
            frame.dispose();
        }
    });

    contentPanel.add(submitButton, gbc);
    mainPanel.add(contentPanel, BorderLayout.CENTER);
    
    frame.add(mainPanel);
    frame.setVisible(true);
}



	 private static void showUpdateParkingLot(ParkingLot selectedLot) {
	        JFrame frame = new JFrame("Enable/Disable Parking Lot");
	        frame.setSize(400, 200);
	        frame.setLocationRelativeTo(null);

	        JPanel panel = new JPanel();
	        panel.setLayout(new GridBagLayout());

	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(10, 10, 10, 10);
	        gbc.gridx = 0;
	        gbc.gridy = 0;

	        JLabel label = new JLabel("Modify Parking Lot: " + selectedLot.getName());
	        label.setFont(new Font("Arial", Font.BOLD, 16));
	        panel.add(label, gbc);

	        gbc.gridy++;
	        JButton enableButton = new JButton("Enable");
	        enableButton.setFont(new Font("Arial", Font.PLAIN, 14));
	        enableButton.addActionListener(e -> {
	            selectedLot.setState(new EnabledState());
	            JOptionPane.showMessageDialog(frame, selectedLot.getName() + " is now Enabled", "Success", JOptionPane.INFORMATION_MESSAGE);
	            frame.dispose();
	        });
	        panel.add(enableButton, gbc);

	        gbc.gridy++;
	        JButton disableButton = new JButton("Disable");
	        disableButton.setFont(new Font("Arial", Font.PLAIN, 14));
	        disableButton.addActionListener(e -> {
	        	System.out.println(selectedLot.getState().isEnabled());
	        	selectedLot.setState(new DisabledState());
	            JOptionPane.showMessageDialog(frame, selectedLot.getName() + " is now Disabled", "Success", JOptionPane.INFORMATION_MESSAGE);
	            System.out.println(selectedLot.getState().isEnabled());
	            frame.dispose();
	        });
	        panel.add(disableButton, gbc);

	        frame.add(panel);
	        frame.setVisible(true);
	    }
	
	 private static void showApproveClient(Client client, String username) {
	        Database db = Database.getInstance();
	        ArrayList<Manager> managers = db.getAllManagers();
	        final Manager[] managerLoggedIn = {null};

	        managerLoggedIn[0] = getSuperManagerLoggedIn(username);

	        
	        JFrame frame = new JFrame("Approve/Disapprove Client");
	        frame.setSize(400, 200);
	        frame.setLocationRelativeTo(null);

	        JPanel panel = new JPanel();
	        panel.setLayout(new GridBagLayout());

	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(10, 10, 10, 10);
	        gbc.gridx = 0;
	        gbc.gridy = 0;

	        JLabel label = new JLabel("Approve/Disapprove Client: " + client.getEmail());
	        label.setFont(new Font("Arial", Font.BOLD, 16));
	        panel.add(label, gbc);

	        gbc.gridy++;
	        JButton enableButton = new JButton("Approve");
	        enableButton.setFont(new Font("Arial", Font.PLAIN, 14));
	        enableButton.addActionListener(e -> {
	            if (managerLoggedIn[0] != null) {
	                managerLoggedIn[0].executeCommand(new ValidateClientRegistrationCommand(client.getEmail()));
	                JOptionPane.showMessageDialog(frame, client.getEmail() + " is now approved", "Success", JOptionPane.INFORMATION_MESSAGE);
	            }
	            frame.dispose();
	        });
	        panel.add(enableButton, gbc);

	        gbc.gridy++;
	        JButton disableButton = new JButton("Disapprove");
	        disableButton.setFont(new Font("Arial", Font.PLAIN, 14));
	        disableButton.addActionListener(e -> {
	            JOptionPane.showMessageDialog(frame, client.getEmail() + " is still disapproved", "Success", JOptionPane.INFORMATION_MESSAGE);
	            frame.dispose();
	        });
	        panel.add(disableButton, gbc);

	        frame.add(panel);
	        frame.setVisible(true);
	    }

	 
	 private static void showClientSelection(String username) {
		    JFrame frame = new JFrame("Select a Client");
		    frame.setSize(400, 400);
		    frame.setLocationRelativeTo(null);

		    JPanel panel = new JPanel();
		    panel.setLayout(new GridBagLayout());

		    GridBagConstraints gbc = new GridBagConstraints();
		    gbc.insets = new Insets(10, 10, 10, 10);
		    gbc.gridx = 0;
		    gbc.gridy = 0;

		    JLabel titleLabel = new JLabel("Select a Client:");
		    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
		    panel.add(titleLabel, gbc);

		    gbc.gridy++;
		    JLabel header = new JLabel("Email | Type");
		    header.setFont(new Font("Arial", Font.BOLD, 14));
		    panel.add(header, gbc);

		    Database db = Database.getInstance();
		    ArrayList<Client> clients = db.getAllClients();

		    ButtonGroup group = new ButtonGroup();
		    final Client[] selectedClient = {null};

		    for (Client client : clients) {
		    	if ((!client.getClientType().equals("visitor"))) {
		    		if(client.getClientType().equals("student")) {
		    			Student s = (Student) client;
		    			if(!s.getAccountApproved()) {
		    				gbc.gridy++;
					        JRadioButton clientButton = new JRadioButton(s.getEmail() + "| " + s.getClientType() + " | "+ s.getAccountApproved());
					        clientButton.setFont(new Font("Arial", Font.PLAIN, 14));
					        group.add(clientButton);
					        panel.add(clientButton, gbc);
					        clientButton.addActionListener(e -> selectedClient[0] = client);
		    			}
		    			
		    		} else if(client.getClientType().equals("nonfaculty")) {
		    			NonFaculty nonFaculty = (NonFaculty) client;
		    			if(!nonFaculty.getAccountApproved()) {
			    			gbc.gridy++;
					        JRadioButton clientButton = new JRadioButton(nonFaculty.getEmail() + "| " + nonFaculty.getClientType() + " | " + nonFaculty.getAccountApproved());
					        clientButton.setFont(new Font("Arial", Font.PLAIN, 14));
					        group.add(clientButton);
					        panel.add(clientButton, gbc);
					        clientButton.addActionListener(e -> selectedClient[0] = client);
		    			}
		    		}
		    		else if(client.getClientType().equals("faculty")) {
		    			Faculty faculty = (Faculty) client;
		    			if(!faculty.getAccountApproved()) {
			    			gbc.gridy++;
					        JRadioButton clientButton = new JRadioButton(faculty.getEmail() + "| " + faculty.getClientType() + " | " + faculty.getAccountApproved());
					        clientButton.setFont(new Font("Arial", Font.PLAIN, 14));
					        group.add(clientButton);
					        panel.add(clientButton, gbc);
					        clientButton.addActionListener(e -> selectedClient[0] = client);
		    			}
		    		}
		    		
		    	}
		    }

		    gbc.gridy++;
		    JButton submitButton = new JButton("Select");
		    submitButton.setFont(new Font("Arial", Font.PLAIN, 14));
		    submitButton.addActionListener(e -> {
		        if (selectedClient[0] == null) {
		            JOptionPane.showMessageDialog(frame, "Please select a client!", "Error", JOptionPane.ERROR_MESSAGE);
		        } else {
		            showApproveClient(selectedClient[0],username);
		            frame.dispose();
		        }
		    });

		    panel.add(submitButton, gbc);
		    frame.add(panel);
		    frame.setVisible(true);
		}

	 
	 private static void showUpdateParkingSpace(ParkingLot lot, ParkingSpace parkingSpace) {
	        JFrame frame = new JFrame("Enable/Disable Parking Space");
	        frame.setSize(400, 200);
	        frame.setLocationRelativeTo(null);

	        JPanel panel = new JPanel();
	        panel.setLayout(new GridBagLayout());

	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(10, 10, 10, 10);
	        gbc.gridx = 0;
	        gbc.gridy = 0;

	        JLabel label = new JLabel("Modify Parking Space: " + parkingSpace.getId() + " in Lot: " + lot.getName());
	        label.setFont(new Font("Arial", Font.BOLD, 16));
	        panel.add(label, gbc);

	        gbc.gridy++;
	        JButton enableButton = new JButton("Enable");
	        enableButton.setFont(new Font("Arial", Font.PLAIN, 14));
	        enableButton.addActionListener(e -> {
	        	parkingSpace.setEnabled(true);
	            JOptionPane.showMessageDialog(frame, "Parking Space " + parkingSpace.getId() + " is now Enabled", "Success", JOptionPane.INFORMATION_MESSAGE);
	            frame.dispose();
	        });
	        panel.add(enableButton, gbc);

	        gbc.gridy++;
	        JButton disableButton = new JButton("Disable");
	        disableButton.setFont(new Font("Arial", Font.PLAIN, 14));
	        disableButton.addActionListener(e -> {
	        	parkingSpace.setEnabled(false);
	            JOptionPane.showMessageDialog(frame, "Parking Space " + parkingSpace.getId() + " is now Disabled", "Success", JOptionPane.INFORMATION_MESSAGE);
	            frame.dispose();
	        });
	        panel.add(disableButton, gbc);

	        frame.add(panel);
	        frame.setVisible(true);
	    }
	
	
	private static void addParkingLotForm(String username) {
	    JFrame formFrame = new JFrame("Parking Lot Details Form");
	    formFrame.setSize(400, 300);
	    formFrame.setLocationRelativeTo(null);
	    JPanel formPanel = new JPanel();
	    formPanel.setLayout(new GridBagLayout());

	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(10, 10, 10, 10);
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.anchor = GridBagConstraints.WEST;

	    formPanel.add(new JLabel("Parking Lot Name:"), gbc);
	    gbc.gridx = 1;
	    JTextField nameField = new JTextField(15);
	    formPanel.add(nameField, gbc);

	    gbc.gridx = 0;
	    gbc.gridy++;
	    formPanel.add(new JLabel("Parking Lot Location:"), gbc);
	    gbc.gridx = 1;
	    JTextField locationField = new JTextField(15);
	    formPanel.add(locationField, gbc);

	    gbc.gridx = 0;
	    gbc.gridy++;
	    gbc.gridwidth = 2;
	    JButton submitButton = new JButton("Submit");
	    submitButton.setFont(new Font("Arial", Font.PLAIN, 14));
	    submitButton.addActionListener(e -> {
	        String id = ParkingLot.randomIdGenerator();
	        String name = nameField.getText().trim();
	        String location = locationField.getText().trim();

	        if (name.isEmpty() || location.isEmpty()) {
	            JOptionPane.showMessageDialog(formFrame, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
	        } else {
	            Database db = Database.getInstance();
	            SuperManager managerLoggedIn = getSuperManagerLoggedIn(username);

	            System.out.println("Before Adding: " + db.getAllParkingLots().size());
	            managerLoggedIn.executeCommand(new AddParkingLotCommand(id, name, location));
	            System.out.println("After Adding: " + db.getAllParkingLots().size());

	            JOptionPane.showMessageDialog(formFrame, "Parking Lot Details Submitted", "Success", JOptionPane.INFORMATION_MESSAGE);
	            formFrame.dispose();
	        }
	    });

	    formPanel.add(submitButton, gbc);
	    formFrame.add(formPanel);
	    formFrame.setVisible(true);
	}
	
	private static void createManagerForm(String username) {
		SuperManager managerLoggedIn = getSuperManagerLoggedIn(username);
        JFrame formFrame = new JFrame("Create Manager Account");
        formFrame.setSize(400, 250);
        formFrame.setLocationRelativeTo(null);
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(15);
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        JTextField lastNameField = new JTextField(15);
        formPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton submitButton = new JButton("Create Account");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 14));
        submitButton.addActionListener(e -> {
        	String firstname = nameField.getText();
            String lastname = lastNameField.getText();
            
	        if (firstname.isEmpty() || lastname.isEmpty()) {
	            JOptionPane.showMessageDialog(formFrame, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
	        } 
	        else {
	        	String[] result = managerLoggedIn.createManagerAccount(firstname, lastname);
	        	String name = result[0], password = result[1];
	        	if (name != null){
	        		JOptionPane.showMessageDialog(formFrame, "Manager account created successfully! Username: " + name + " | Password: " + password);
	                formFrame.dispose();
	        	}
	        	else {
	        		JOptionPane.showMessageDialog(formFrame, "Something went wrong with creating an account!", "Error", JOptionPane.ERROR_MESSAGE);
	        	}
	        }
	    });
        formPanel.add(submitButton, gbc);

        formFrame.add(formPanel);
        formFrame.setVisible(true);
    }


}
