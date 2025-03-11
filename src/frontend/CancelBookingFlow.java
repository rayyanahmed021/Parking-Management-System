package frontend;

import backend.*; 
import javax.swing.*;
import java.awt.*;

public class CancelBookingFlow {
    private JFrame frame;
    private Client client;
    private JComboBox<Booking> bookingDropdown;

    public CancelBookingFlow(Client client) {
        this.client = client;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Cancel Booking");
        frame.setSize(500, 300); // Increased width for better spacing
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null); // Centers the frame on screen

        // Title Label (Centered and Styled)
        JLabel titleLabel = new JLabel("Select a Booking to Cancel:", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Dropdown Panel (Centered)
        JPanel centerPanel = new JPanel(new GridBagLayout()); // Better alignment
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        bookingDropdown = new JComboBox<>(client.getBookings().toArray(new Booking[0]));
        bookingDropdown.setPreferredSize(new Dimension(350, 30)); // Increased size
        centerPanel.add(bookingDropdown, gbc);

        frame.add(centerPanel, BorderLayout.CENTER);

        // Buttons Panel (Aligned at Bottom and Centered)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton cancelButton = new JButton("Cancel Booking");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.addActionListener(e -> {
            Booking selectedBooking = (Booking) bookingDropdown.getSelectedItem();
            processCancel(selectedBooking);
        });

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> {
            frame.dispose();
            new OptionsScreen(client);
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void processCancel(Booking selectedBooking) {
        if (selectedBooking == null) {
            JOptionPane.showMessageDialog(frame, "No booking selected.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to cancel this booking?\nA refund will be issued if applicable.",
                "Confirm Cancellation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Payment payment = selectedBooking.getPayment();

            if (payment != null && !payment.getIsRefunded() && payment.getTotal() > 0) {
                JOptionPane.showMessageDialog(frame, "You are eligle for a refund. Redirecting to Refund Screen...");
                frame.dispose(); // Close CancelBookingFlow window
                new RefundScreen(client, selectedBooking, this); // Redirect to RefundScreen
                return;
            }

            // Proceed with cancellation
            client.getBookings().remove(selectedBooking);
            JOptionPane.showMessageDialog(frame, "Booking canceled. Refund issued to original payment method.");

            try {
                Database.getInstance().updateBookings("src/bookingData.csv");
            } catch (Exception e) {
                e.printStackTrace();
            }

            frame.dispose(); // Close the CancelBookingFlow window after cancellation
        }
    }
    public void completeCancellation(Booking selectedBooking, boolean refundProcessed) {
        client.getBookings().remove(selectedBooking);

//        JOptionPane.showMessageDialog(null, refundProcessed
//                ? "Booking canceled and refund processed."
//                : "Booking canceled. No refund issued.");

        // Update bookings CSV
        try {
            Database.getInstance().updateBookings("src/bookingData.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame.dispose();
       // new OptionsScreen(client); // After refund or cancel, return to OptionsScreen
    }

    /**
     * Completes the cancellation of the booking after refund processing.
     */
   
}
