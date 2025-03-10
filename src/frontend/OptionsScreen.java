package frontend;

import backend.Client;
import backend.Booking;
import backend.Payment;

import javax.swing.*;
import java.awt.*;

public class OptionsScreen {
    private JFrame frame;
    private Client client;

    public OptionsScreen(Client client) {
        this.client = client;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Client Options");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Center the window on the screen
        //frame.setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Please Choose an Option:", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10)); // Top padding added
        frame.add(titleLabel, BorderLayout.NORTH);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));

        JButton viewBookingsBtn = new JButton("View Bookings");
        JButton newBookingBtn = new JButton("Make a New Booking");
        JButton editBookingBtn = new JButton("Modify an Existing Booking");
        JButton cancelBookingBtn = new JButton("Cancel a Booking");
        JButton exitBtn = new JButton("Exit");

        // Set button alignment for a cleaner UI
        viewBookingsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        newBookingBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        editBookingBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelBookingBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add buttons with spacing
        buttonPanel.add(viewBookingsBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(newBookingBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(editBookingBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(cancelBookingBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(exitBtn);

        frame.add(buttonPanel, BorderLayout.CENTER);

        // Button actions
        viewBookingsBtn.addActionListener(e -> openScreen("ViewBookingScreen"));
        newBookingBtn.addActionListener(e -> openScreen("NewBookingScreen"));
        editBookingBtn.addActionListener(e -> openScreen("EditBookingScreen"));
        cancelBookingBtn.addActionListener(e -> {
            new CancelBookingFlow(client); // Open CancelBookingFlow in a new window
        });


        exitBtn.addActionListener(e -> handleExit());

        frame.setVisible(true);
    }

    private void openScreen(String screenName) {
        frame.dispose(); // Close current screen before opening new one

        switch (screenName) {
            case "ViewBookingScreen":
                new ViewBookingScreen(client);
                break;
            case "EditBookingScreen":
                new EditBookingFlow(client);
                break;
            case "CancelBookingScreen":
                new CancelBookingFlow(client); // Fix: Now opens CancelBookingFlow
                break;
            default:
                JOptionPane.showMessageDialog(frame, "Feature not implemented yet.");
                break;
        }
    }


    private void handleExit() {
        if (hasOutstandingBalance()) {
            int confirm = JOptionPane.showConfirmDialog(
                frame,
                "You have an outstanding balance. Do you want to proceed to payment?",
                "Outstanding Balance",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose();
                new PaymentScreen(client); // Redirect to payment
            }
        } else {
            int confirmExit = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to exit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmExit == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frame, "Thank you! Exiting application.");
                System.exit(0); // Exit application only after confirmation
            }
        }
    }



    private boolean hasOutstandingBalance() {
        for (Booking booking : client.getBookings()) {
            Payment payment = booking.getPayment();
            if (payment != null && !payment.getIsRefunded() && payment.getTotal() > 0) {
                return true;
            }
        }
        return false;
    }
}
