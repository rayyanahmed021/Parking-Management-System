package frontend;

import backend.Client;
import backend.ParkingLot;
import backend.ParkingSpace;
import backend.Booking;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewBookingScreen {
    private JFrame frame;
    private Client client;
    private JPanel bookingPanel;

    public ViewBookingScreen(Client client) {
        this.client = client;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Your Bookings");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Your Bookings", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(titleLabel, BorderLayout.NORTH);

        bookingPanel = new JPanel();
        bookingPanel.setLayout(new BoxLayout(bookingPanel, BoxLayout.Y_AXIS));

        displayBookings();

        frame.add(new JScrollPane(bookingPanel), BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Back to Options");
        backButton.addActionListener(e -> {
            frame.dispose();
            new OptionsScreen(client); // Redirect back to options
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void displayBookings() {
        bookingPanel.removeAll(); // Clear previous content
        for (Booking booking : client.getBookings()) {
            String spaceId = (booking.getParkingSpace() != null) ? String.valueOf(booking.getParkingSpace().getId()) : "N/A";
            String lotId = (booking.getParkingLot() != null) ? booking.getParkingLot().getId() : "N/A";

            JLabel bookingLabel = new JLabel("Booking " + booking.getID() +
                    " | Lot: " + lotId + " | Space: " + spaceId +
                    " | Amount: $" + booking.getTotalPrice());

            bookingPanel.add(bookingLabel);
        }
        bookingPanel.revalidate();
        bookingPanel.repaint();
    }


}
