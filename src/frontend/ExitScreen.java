package frontend;

import backend.*;
import javax.swing.*;
import java.awt.*;

public class ExitScreen {
    private JFrame frame;
    private Client client;

    public ExitScreen(Client client) {
        this.client = client;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Exit Confirmation");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Are you sure you want to exit?", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(titleLabel, BorderLayout.NORTH);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> handleExit());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> frame.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(exitButton);
        buttonPanel.add(cancelButton);

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void handleExit() {
        for (Booking booking : client.getBookings()) {
            Payment payment = booking.getPayment();
            if (payment != null && !payment.getIsRefunded() && payment.getTotal() > 0) {
                JOptionPane.showMessageDialog(frame, "You have an outstanding balance. Redirecting to Payment Screen.");
                new PaymentScreen(client);
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "Exiting application.");
        System.exit(0);
    }
}
