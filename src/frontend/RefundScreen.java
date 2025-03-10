package frontend;

import backend.Client;
import backend.Booking;
import backend.Database;
import backend.Payment;

import javax.swing.*;
import java.awt.*;

public class RefundScreen {
    private JFrame frame;
    private Client client;
    private Booking booking;
    private CancelBookingFlow cancelBookingFlow;

    public RefundScreen(Client client, Booking booking, CancelBookingFlow cancelBookingFlow) {
        this.client = client;
        this.booking = booking;
        this.cancelBookingFlow = cancelBookingFlow; // Store reference to CancelBookingFlow
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Refund Processing");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JLabel messageLabel = new JLabel("Your refund request is being processed...", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        frame.add(messageLabel, BorderLayout.CENTER);

        JButton confirmRefundButton = new JButton("Confirm Refund");
        confirmRefundButton.addActionListener(e -> processRefund());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmRefundButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void processRefund() {
        Payment payment = booking.getPayment();
        if (payment != null && !payment.getIsRefunded()) {
            payment.setIsRefunded(true);

            // Update payments CSV to reflect refund
            try {
                Database.getInstance().updatePayments("src/paymentData.csv");
            } catch (Exception e) {
                e.printStackTrace();
            }

            JOptionPane.showMessageDialog(frame, "Refund has been successfully processed to your original booking method.");
        }

        frame.dispose(); // Close refund screen
        cancelBookingFlow.completeCancellation(booking, true); // Proceed to cancel booking
    }
}
