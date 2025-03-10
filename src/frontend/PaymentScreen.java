package frontend;

import backend.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PaymentScreen {
    private JFrame frame;
    private Client client;
    private JPanel paymentPanel;

    public PaymentScreen(Client client) {
        this.client = client;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Payment Screen");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Pending Payments", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(titleLabel, BorderLayout.NORTH);

        paymentPanel = new JPanel();
        paymentPanel.setLayout(new BoxLayout(paymentPanel, BoxLayout.Y_AXIS));

        displayPendingPayments();

        frame.add(new JScrollPane(paymentPanel), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void displayPendingPayments() {
        paymentPanel.removeAll();
        ArrayList<Booking> bookings = client.getBookings();

        boolean hasPayments = false;
        for (Booking booking : bookings) {
            Payment payment = booking.getPayment();
            if (payment != null && !payment.getIsRefunded() && payment.getTotal() > 0) {
                hasPayments = true;

                JPanel paymentItem = new JPanel(new FlowLayout());
                paymentItem.setBorder(BorderFactory.createTitledBorder("Booking ID: " + booking.getID()));
                
                JLabel paymentLabel = new JLabel(
                        "Total: $" + payment.getTotal() + " | Method: " + payment.getPaymentMethod()
                );

                JButton payBtn = new JButton("Pay");
                JButton refundBtn = new JButton("Refund");

                payBtn.addActionListener(e -> processPayment(payment));
                refundBtn.addActionListener(e -> processRefund(payment));

                paymentItem.add(paymentLabel);
                paymentItem.add(payBtn);
                paymentItem.add(refundBtn);
                paymentPanel.add(paymentItem);
            }
        }

        if (!hasPayments) {
            paymentPanel.add(new JLabel("No pending payments."));
            JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> {
                frame.dispose();
                new OptionsScreen(client);
            });
            paymentPanel.add(backButton);
        }

        paymentPanel.revalidate();
        paymentPanel.repaint();
    }

    private void processPayment(Payment payment) {
        int confirm = JOptionPane.showConfirmDialog(frame, "Confirm payment of $" + payment.getTotal() + "?");
        if (confirm == JOptionPane.YES_OPTION) {
            payment.setIsRefunded(true);
            JOptionPane.showMessageDialog(frame, "Payment processed successfully.");
            
            try {
                Database.getInstance().updatePayments("src/paymentData.csv");  // Save updated payment status
            } catch (Exception e) {
                e.printStackTrace();
            }

            frame.dispose();
            new PaymentScreen(client); // Refresh the screen
        }
    }

    private void processRefund(Payment payment) {
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to request a refund?");
        if (confirm == JOptionPane.YES_OPTION) {
            payment.setIsRefunded(true);
            JOptionPane.showMessageDialog(frame, "Refund request processed successfully.");
            
            try {
                Database.getInstance().updatePayments("src/paymentData.csv");  // Save refund status
            } catch (Exception e) {
                e.printStackTrace();
            }

            frame.dispose();
            new PaymentScreen(client); // Refresh the screen
        }
    }
}
