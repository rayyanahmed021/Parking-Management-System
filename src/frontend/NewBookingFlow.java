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
        frame.setSize(400, 300);
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

        JButton selectButton = new JButton("Next");
        selectButton.addActionListener(e -> {
            if (selectedLot != null) {
                showLicenseAndTimeInput();
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a parking lot.");
            }
        });
        frame.add(selectButton, BorderLayout.SOUTH);
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.dispose();
            new OptionsScreen(client);
        });
        lotPanel.add(backButton);

        frame.setVisible(true);
    }

    private void showLicenseAndTimeInput() {
        frame.getContentPane().removeAll();
        frame.setTitle("Enter Booking Details");

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

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.getContentPane().add(nextButton, BorderLayout.SOUTH);
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

        JPanel spacePanel = new JPanel();
        spacePanel.setLayout(new BoxLayout(spacePanel, BoxLayout.Y_AXIS));

        List<ParkingSpace> availableSpaces = new ArrayList<>();
        ParkingSpace[] allSpaces = selectedLot.getParkingSpaces(); // Assuming this returns an array

        for (ParkingSpace space : allSpaces) {
        	if (space.isEnabled()) {
        		for (Booking bookings : db.getAllBookings()) {
        			if (bookings.getParkingSpace() == space) {
        				if (!(newBooking.getStartTime().isBefore(bookings.getEndTime()) && bookings.getStartTime().isBefore(newBooking.getEndTime()))) {
        					availableSpaces.add(space);
        				}
        			}
        		}
        	}
        }

        ButtonGroup group = new ButtonGroup();
        for (ParkingSpace space : availableSpaces) {
            JRadioButton radioButton = new JRadioButton("Space: " + space.getId());
            group.add(radioButton);
            spacePanel.add(radioButton);
            radioButton.addActionListener(e -> newBooking.setParkingSpace(space));
        }

        JScrollPane scrollPane = new JScrollPane(spacePanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Confirm and Proceed to Payment");
        confirmButton.addActionListener(e -> {
            if (newBooking.getParkingSpace() != null) {
            	// Test Cases DO NOT REMOVE
            	// System.out.println(newBooking.getTotalPrice());
            	// System.out.println(client.selectSpace(newBooking));
            	client.selectSpace(newBooking);
            	// System.out.println(newBooking.getTotalPrice());
                frame.dispose();
                new PaymentScreen(client);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a parking space.");
            }
        });
        frame.add(confirmButton, BorderLayout.SOUTH);

        frame.revalidate();
        frame.repaint();
        
    }
}
