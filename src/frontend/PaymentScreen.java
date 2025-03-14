package frontend;

import backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class PaymentScreen {
    private JFrame frame;
    private Client client;
    private Booking booking;
    private double amount;
    private JComboBox<String> paymentMethodDropdown;
    private JPanel inputPanel;
    private JTextField cardNumberField, cardHolderField, cvvField, expiryField, emailField, passwordField, mobileNumberField, providerField;

    public PaymentScreen(Client client, Booking booking, double amount) {
        this.client = client;
        this.booking = booking;
        this.amount = amount;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Payment");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Select Payment Method:", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        String[] paymentMethods = {"Credit Card", "Debit Card", "PayPal", "Mobile"};
        paymentMethodDropdown = new JComboBox<>(paymentMethods);
        paymentMethodDropdown.addActionListener(e -> updateInputFields());

        JPanel dropdownPanel = new JPanel();
        dropdownPanel.add(new JLabel("Payment Method:"));
        dropdownPanel.add(paymentMethodDropdown);
        centerPanel.add(dropdownPanel);

        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 5, 5));
        centerPanel.add(inputPanel);

        updateInputFields();

        frame.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton payButton = new JButton("Confirm Payment");
        payButton.addActionListener(e -> processPayment());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> frame.dispose());

        buttonPanel.add(payButton);
        buttonPanel.add(cancelButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void updateInputFields() {
        inputPanel.removeAll();
        String selectedMethod = (String) paymentMethodDropdown.getSelectedItem();

        if ("Credit Card".equals(selectedMethod) || "Debit Card".equals(selectedMethod)) {
            inputPanel.add(new JLabel("Card Number:"));
            cardNumberField = new JTextField();
            inputPanel.add(cardNumberField);

            inputPanel.add(new JLabel("Card Holder Name:"));
            cardHolderField = new JTextField();
            inputPanel.add(cardHolderField);

            inputPanel.add(new JLabel("CVV:"));
            cvvField = new JTextField();
            inputPanel.add(cvvField);

            inputPanel.add(new JLabel("Expiry Date (MM/YY):"));
            expiryField = new JTextField();
            inputPanel.add(expiryField);
        } else if ("PayPal".equals(selectedMethod)) {
            inputPanel.add(new JLabel("PayPal Email:"));
            emailField = new JTextField();
            inputPanel.add(emailField);
            
            inputPanel.add(new JLabel("PayPal Password:"));
            passwordField = new JPasswordField();
            inputPanel.add(passwordField);
            
        } else if ("Mobile".equals(selectedMethod)) {
            inputPanel.add(new JLabel("Mobile Number:"));
            mobileNumberField = new JTextField();
            inputPanel.add(mobileNumberField);

            inputPanel.add(new JLabel("Provider:"));
            providerField = new JTextField();
            inputPanel.add(providerField);
        }

        inputPanel.revalidate();
        inputPanel.repaint();
    }

    private void processPayment() {
        String selectedMethod = (String) paymentMethodDropdown.getSelectedItem();
        PaymentStrategy paymentStrategy = null;

        if ("Credit Card".equals(selectedMethod) || "Debit Card".equals(selectedMethod)) {
            try {
                long cardNumber = Long.parseLong(cardNumberField.getText());
                String cardHolder = cardHolderField.getText();
                String cvv = cvvField.getText();
                String expiry = expiryField.getText();

                if (cardHolder.isEmpty() || cvv.length() != 3 || expiry.length() != 5) {
                    JOptionPane.showMessageDialog(frame, "Invalid card details!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                paymentStrategy = "Credit Card".equals(selectedMethod)
                        ? new CreditCardStrategy(cardNumber, cardHolder, cvv, expiry)
                        : new DebitCardStrategy(cardNumber, cardHolder, cvv, expiry);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid card number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else if ("PayPal".equals(selectedMethod)) {
            String email = emailField.getText();
            String password = passwordField.getText();
            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(frame, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            paymentStrategy = new PayPalStrategy(email, password);
        } else if ("Mobile".equals(selectedMethod)) {
            String mobileNumber = mobileNumberField.getText();
            String provider = providerField.getText();
            if (!mobileNumber.matches("\\d{10}") || provider.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Invalid mobile payment details!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            paymentStrategy = new MobilePaymentStrategy(mobileNumber, provider);
        }

        if (paymentStrategy != null) {
        	Database db = Database.getInstance();

            Payment processedPayment = new Payment(amount, false, paymentStrategy);
            db.getAllPayments().add(processedPayment); 
            
            booking.setPayment(processedPayment);

            JOptionPane.showMessageDialog(frame, "Payment successful!");
            frame.dispose();
            new OptionsScreen(client);
        }
    }
}
