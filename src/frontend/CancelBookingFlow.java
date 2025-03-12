package frontend;

import backend.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

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
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Select a Booking to Cancel:", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // **Filter only bookings that are NOT refunded**
        List<Booking> eligibleBookings = client.getBookings().stream()
                .filter(booking -> booking.getPayment() != null && !booking.getPayment().getIsRefunded())
                .collect(Collectors.toList());

        if (eligibleBookings.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No bookings available for cancellation.", "Info", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            new OptionsScreen(client);
            return;
        }

        bookingDropdown = new JComboBox<>(eligibleBookings.toArray(new Booking[0]));
        bookingDropdown.setPreferredSize(new Dimension(350, 30));
        centerPanel.add(bookingDropdown, gbc);

        frame.add(centerPanel, BorderLayout.CENTER);

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
            double refundAmount = selectedBooking.checkRefund();

            if (refundAmount > 0) {
                JOptionPane.showMessageDialog(frame, "You are eligible for a refund of $" + refundAmount +
                        ". Redirecting to Refund Screen...");
                frame.dispose();
                new RefundScreen(client, selectedBooking, this);
                return;
            }

            // No refund case
            client.getBookings().remove(selectedBooking);
            JOptionPane.showMessageDialog(frame, "Booking canceled. No refund issued.");


            frame.dispose();
        }
    }

    /**
     * Completes the cancellation of the booking after refund processing.
     */
    public void completeCancellation(Booking selectedBooking, boolean refundProcessed) {
        client.getBookings().remove(selectedBooking);

//        JOptionPane.showMessageDialog(null, refundProcessed
//                ? "Booking canceled and refund processed."
//                : "Booking canceled. No refund issued.");

        try {
            Database.getInstance().updateBookings("src/bookingData.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.dispose();
    }
}
