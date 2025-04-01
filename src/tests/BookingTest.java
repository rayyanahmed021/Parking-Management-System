package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;
import java.time.Duration;
import backend.*;

public class BookingTest {

    private Booking booking;
    private Client client;
    private ParkingLot parkingLot;
    private ParkingSpace parkingSpace;
    private Payment payment;

    @Before
    public void Test1() {
        client = new Student("John Doe", "123456789", true);
        parkingLot = new ParkingLot("PL123", "Test Lot", new EnabledState(), new ParkingSpace[100], "123 Test St.");
        parkingSpace = new ParkingSpace(1, parkingLot, true);

        PaymentStrategy paymentStrategy = new CreditCardStrategy(411111111, "John Doe", "12/25", "123");
        payment = new Payment(100.0, false, paymentStrategy);

        booking = new Booking(1, client, 50.0, "ABC123", 
                              LocalDateTime.now().minusHours(2), LocalDateTime.now(), payment, parkingSpace, parkingLot);
    }

    @Test
    public void test2() {
        Booking b = new Booking();
        assertNotNull(b.getID());
    }

    @Test
    public void test3() {
        booking.setID(5);
        assertEquals(5, booking.getID());
    }

    @Test
    public void test4() {
        assertEquals(client, booking.getClient());
    }

    @Test
    public void test5() {
        Client newClient = new Student("Jane Smith", "987654321", true);
        booking.setClient(newClient);
        assertEquals(newClient, booking.getClient());
    }

    @Test
    public void test6() {
        assertEquals("ABC123", booking.getLicensePlate());
    }

    @Test
    public void test7() {
        booking.setLicensePlate("XYZ789");
        assertEquals("XYZ789", booking.getLicensePlate());
    }

    @Test
    public void test8() {
        LocalDateTime newStartTime = LocalDateTime.now().minusHours(5);
        booking.setStartTime(newStartTime);
        assertEquals(newStartTime, booking.getStartTime());
    }

    @Test
    public void test9() {
        assertNotNull(booking.getEndTime());
        assertEquals(LocalDateTime.now(), booking.getEndTime());
    }

    @Test
    public void test10() {
        LocalDateTime newEndTime = LocalDateTime.now().minusHours(1);
        booking.setEndTime(newEndTime);
        assertEquals(newEndTime, booking.getEndTime());
    }

    @Test
    public void test11() {
        double refund = booking.checkRefund();
        assertEquals(-1.0, refund, 0.001);

        booking.setStartTime(LocalDateTime.now().minusHours(3));
        double refundAfterOneHour = booking.checkRefund();
        assertEquals(-1, refundAfterOneHour, 0.001);
    }

    @Test
    public void test12() {
        double checkoutAmount = booking.calculateCheckout();
        assertEquals(10.0, checkoutAmount, 0.001);
    }

    @Test
    public void test13() {
        String expectedString = "Booking ID: 4 | License Plate: ABC123 | Start: " + booking.getStartTime() + " | End: " + booking.getEndTime();
        assertNotNull(booking.toString());
    }

    @Test
    public void test14() {
        assertEquals("Credit Card", booking.getPayment().getPaymentMethod());
    }
    
    @Test
    public void test15() {
        Booking b = new Booking();
        b.setTotalPrice(10);
        assertNotNull(b.getTotalPrice());
    }
    
    @Test
    public void test16() {
        Booking b = new Booking();
        PaymentStrategy paymentStrategy = new CreditCardStrategy(411111111, "John Doe", "12/25", "123");
        payment = new Payment(100.0, false, paymentStrategy);
        parkingLot = new ParkingLot("PL123", "Test Lot", new EnabledState(), new ParkingSpace[100], "123 Test St.");
        parkingSpace = new ParkingSpace(1, parkingLot, true);
        b.setParkingLot(parkingLot);
        b.setParkingSpace(parkingSpace);
        b.setPayment(payment);
        assertNotNull(b.getParkingLot());
        assertNotNull(b.getParkingSpace());
    }
}
