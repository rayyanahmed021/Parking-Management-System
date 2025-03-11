package frontend;

import backend.Client;
import backend.Booking;
import backend.Payment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class EditBookingFlow {
	private JFrame frame;
    private Client client;
    private JComboBox<Booking> bookingDropdown;
    private JTextField startTimeField;
    private JTextField endTimeField;

    public EditBookingFlow(Client client) {
        this.client = client;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Edit Booking");
        frame.setSize(450, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Select and modify one of your bookings", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Main panel for inputs
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Booking selection dropdown
        JLabel selectBookingLabel = new JLabel("Select Booking:");
        bookingDropdown = new JComboBox<>(getBookingsArray());
        panel.add(selectBookingLabel);
        panel.add(bookingDropdown);

        // Start time input
        JLabel startTimeLabel = new JLabel("New Start Time (yyyy-MM-dd HH:mm):");
        startTimeField = new JTextField();
        panel.add(startTimeLabel);
        panel.add(startTimeField);

        // End time input
        JLabel endTimeLabel = new JLabel("New End Time (yyyy-MM-dd HH:mm):");
        endTimeField = new JTextField();
        panel.add(endTimeLabel);
        panel.add(endTimeField);

        frame.add(panel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton submitButton = new JButton("Submit Changes");
        JButton backButton = new JButton("Back");

        submitButton.addActionListener(e -> processBookingEdit());
        backButton.addActionListener(e -> goBack());

        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private Booking[] getBookingsArray() {
        List<Booking> bookings = client.getBookings();
        return bookings.toArray(new Booking[0]);
    }

    private void processBookingEdit() {
        try {
            Booking selectedBooking = (Booking) bookingDropdown.getSelectedItem();
            if (selectedBooking == null) {
                JOptionPane.showMessageDialog(frame, "Please select a booking to modify.");
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime newStartTime = LocalDateTime.parse(startTimeField.getText().trim(), formatter);
            LocalDateTime newEndTime = LocalDateTime.parse(endTimeField.getText().trim(), formatter);
            
            LocalDateTime[] change = new LocalDateTime[] {newStartTime, newEndTime}; 
            boolean updateSuccess = client.updateParking("Edit", change, selectedBooking);
            
            if (updateSuccess) {
                JOptionPane.showMessageDialog(frame, "Booking updated successfully.");
                PaymentScreen p = new PaymentScreen(client);
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Selected time slot is unavailable. Try a different time.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid date format. Use yyyy-MM-dd HH:mm.");
        }
    }

    private void goBack() {
        new OptionsScreen(client);
        frame.dispose();
    }
}
