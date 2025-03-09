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
    private JButton submitButton;

    public EditBookingFlow(Client client) {
        this.client = client;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Edit Booking");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 1));

        JLabel selectLabel = new JLabel("Select a booking to edit:");
        bookingDropdown = new JComboBox<>(client.getBookings().toArray(new Booking[0]));
        
        JLabel startTimeLabel = new JLabel("Enter new start time (yyyy-MM-dd HH:mm):");
        startTimeField = new JTextField();
        JLabel endTimeLabel = new JLabel("Enter new end time (yyyy-MM-dd HH:mm):");
        endTimeField = new JTextField();
        
        submitButton = new JButton("Submit Changes");
        submitButton.addActionListener(e -> processBookingEdit());
        
        frame.add(selectLabel);
        frame.add(bookingDropdown);
        frame.add(startTimeLabel);
        frame.add(startTimeField);
        frame.add(endTimeLabel);
        frame.add(endTimeField);
        frame.add(submitButton);
        
        frame.setVisible(true);
    }

    private void processBookingEdit() {
        Booking selectedBooking = (Booking) bookingDropdown.getSelectedItem();
        if (selectedBooking == null) {
            JOptionPane.showMessageDialog(frame, "No booking selected.");
            return;
        }

        String startTimeText = startTimeField.getText().trim();
        String endTimeText = endTimeField.getText().trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try {
            LocalDateTime newStartTime = LocalDateTime.parse(startTimeText, formatter);
            LocalDateTime newEndTime = LocalDateTime.parse(endTimeText, formatter);
            
            LocalDateTime[] change = new LocalDateTime[] {newStartTime, newEndTime}; 
            
            boolean updateSuccess = client.updateParking("Edit", change, selectedBooking);
            
            if (updateSuccess) {
                JOptionPane.showMessageDialog(frame, "Booking updated successfully. Redirecting to Payment.");
//                PaymentScreen.startGUI(client);
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "The selected time slot is unavailable. Please choose a different time.");
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid date/time format. Please enter the time in yyyy-MM-dd HH:mm format.");
        }
    }
}
