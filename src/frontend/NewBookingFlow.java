package frontend;

import backend.Client;
import backend.Database;
import backend.ParkingLot;
import backend.ParkingSpace;
import backend.Booking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NewBookingFlow {
    private JFrame frame;
    private Client client;
    private ParkingLot selectedLot;
    private Booking newBooking;
    private Database db = Database.getInstance();
    private String lotID;

    public NewBookingFlow(Client client) {
        this.client = client;
        showParkingLotSelection();
    }

    private void showParkingLotSelection() {
        frame = new JFrame("Select a Parking Lot");
        frame.setSize(550, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Select a Parking Lot:", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel lotPanel = new JPanel();
        lotPanel.setLayout(new BoxLayout(lotPanel, BoxLayout.Y_AXIS));

        ArrayList<ParkingLot> availableLots = new ArrayList<>();
        for (ParkingLot p : db.getAllParkingLots()) {
        	if (p.getState().isEnabled()) {
        		availableLots.add(p);
        	}
        }

        ButtonGroup group = new ButtonGroup();
        for (ParkingLot lot : availableLots) {
            JRadioButton radioButton = new JRadioButton("Lot: " + lot.getName());
            group.add(radioButton);
            lotPanel.add(radioButton);
            radioButton.addActionListener(e -> {
            	this.lotID = lot.getId();
            	selectedLot = lot;
            });
        }

        JScrollPane scrollPane = new JScrollPane(lotPanel);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.dispose();
            new OptionsScreen(client);
        });
        JButton selectButton = new JButton("Next");
        selectButton.addActionListener(e -> {
            if (selectedLot != null) {
                showLicenseAndTimeInput();
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a parking lot.");
            }
        });
        frame.add(selectButton, BorderLayout.SOUTH);
        
        buttonPanel.add(selectButton);
        buttonPanel.add(backButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void showLicenseAndTimeInput() {
        frame.getContentPane().removeAll();
        frame.setTitle("Enter Booking Details");
        
        JLabel titleLabel = new JLabel("Provide Booking Information", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel licenseLabel = new JLabel("License Plate:");
        JTextField licenseField = new JTextField();
        JLabel startLabel = new JLabel("Start Time (yyyy-MM-dd HH:mm):");
        JTextField startField = new JTextField();
        JLabel endLabel = new JLabel("End Time (yyyy-MM-dd HH:mm):");
        JTextField endField = new JTextField();

        panel.add(licenseLabel);
        panel.add(licenseField);
        panel.add(startLabel);
        panel.add(startField);
        panel.add(endLabel);
        panel.add(endField);

        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showParkingLotSelection());
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            try {
                String license = licenseField.getText();
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime startTime = LocalDateTime.parse(startField.getText().trim(), formatter);
                LocalDateTime endTime = LocalDateTime.parse(endField.getText().trim(), formatter);
                
                if (!client.isValidLicensePlate(license)) {
                    JOptionPane.showMessageDialog(frame, "Invalid license plate format.");
                    return;
                }
                
                Duration duration = Duration.between(startTime, endTime);
                long hours = duration.toHours();
                if (hours < 1) {
                	JOptionPane.showMessageDialog(frame, "Must book at least 1 hour");
                	return;
                }
                
                newBooking = new Booking();
                newBooking.setClient(client);
                newBooking.setParkingLot(selectedLot);
                newBooking.setStartTime(startTime);
                newBooking.setEndTime(endTime);
                newBooking.setLicensePlate(license);
                newBooking.setTotalPrice(0);
                newBooking.getParkingLot().setId(this.lotID);
                
                showParkingSpaceSelection();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid date format. Use yyyy-MM-dd HH:mm.");
            }
        });

        buttonPanel.add(nextButton);
        buttonPanel.add(backButton);
        
        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.revalidate();
        frame.repaint();
        
//        JButton backButton = new JButton("Back");
//        backButton.addActionListener(e -> showParkingLotSelection());
//        frame.add(backButton);
        
        frame.setVisible(true);
    }

    private void showParkingSpaceSelection() {
        frame.getContentPane().removeAll();
        frame.setTitle("Select a Parking Space");
        
        JLabel titleLabel = new JLabel("Select a Parking Space", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel spacePanel = new JPanel();
        spacePanel.setLayout(new BoxLayout(spacePanel, BoxLayout.Y_AXIS));

        ArrayList<ParkingSpace> availableSpaces = new ArrayList<>();

        ParkingSpace[] allSpaces = selectedLot.getParkingSpaces();
        for (ParkingSpace space : allSpaces) {
            if (space.isEnabled()) {
                boolean isConflict = false;
                for (Booking b : db.getAllBookings()) {
                    if (b.getParkingSpace() != null && b.getParkingSpace().getId() == space.getId() && b.getParkingLot().getId().equals(space.getParkingLot().getId())) {
                        if (newBooking.getStartTime().isBefore(b.getEndTime()) 
                            && b.getStartTime().isBefore(newBooking.getEndTime())) 
                        {
                            isConflict = true;
                            break;
                        }
                    }
                }
                if (!isConflict) {
                    availableSpaces.add(space);
                }
            }
        }

        // Create a radio button for each available space
        ButtonGroup group = new ButtonGroup();
        for (ParkingSpace space : availableSpaces) {
            JRadioButton radioButton = new JRadioButton("Space: " + space.getId());
            group.add(radioButton);
            spacePanel.add(radioButton);
            radioButton.addActionListener(e -> newBooking.setParkingSpace(space));
        }

        // Wrap spacePanel in a scroll pane for large lists
        JScrollPane scrollPane = new JScrollPane(spacePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Confirm button
        JButton confirmButton = new JButton("Confirm and Proceed to Payment");
        confirmButton.addActionListener(e -> {
            if (newBooking.getParkingSpace() != null) {
                // Link the selected space to the new booking
                client.selectSpace(newBooking);
                
                double depositAmount = client.calculateDepositClient();
                newBooking.setTotalPrice(depositAmount);
                
                ArrayList<Booking> bookings = db.getAllBookings();
                bookings.add(newBooking);
                // Close the frame and show payment screen
                frame.dispose();
                new PaymentScreen(client, newBooking, depositAmount);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a parking space.");
            }
        });
        mainPanel.add(confirmButton, BorderLayout.SOUTH);
        
        frame.add(mainPanel);
        frame.revalidate();
        frame.repaint();
    }
}