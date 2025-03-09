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
        newBookingBtn.addActionListener(e -> openScreen("NewBookingFlow"));
        editBookingBtn.addActionListener(e -> openScreen("EditBookingFlow"));
        cancelBookingBtn.addActionListener(e -> openScreen("CancelBookingFlow"));
        exitBtn.addActionListener(e -> handleExit());

        frame.setVisible(true);
    }

    private void openScreen(String screenName) {
    	frame.dispose(); // Close the current window

        switch (screenName) {
            case "ViewBookingScreen":
//                new ViewBookingScreen(client);
                break;
            case "NewBookingFlow":
//                new NewBookingFlow(client);
                break;
            case "EditBookingFlow":
                new EditBookingFlow(client);
                break;
            case "CancelBookingFlow":
//                new CancelBookingFlow(client);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid screen: " + screenName);
                break;
        }
    }

    private void handleExit() {
        if (hasOutstandingBalance()) {
            JOptionPane.showMessageDialog(frame, "You have an outstanding balance. Redirecting to Payment Screen.");
//            PaymentScreen.startGUI(client);
        } else {
            JOptionPane.showMessageDialog(frame, "Exiting application.");
            System.exit(0);
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
