package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import backend.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ClientTest {
    private Client client;
    private Database database;
    private ParkingLot parkingLot;
    private ParkingSpace[] parkingSpaces;
    private Booking booking;
    private ParkingLotState state;
    private Payment payment;
    private PaymentStrategy p;
    
    @Before
    public void test1() {
        database = Database.getInstance();
        client = new Client("test@example.com", "Password123!") {
            @Override
            public String getClientType() {
                return "Regular";
            }
            
            @Override
            public double calculateDepositClient() {
                return 10.0;
            }
        };
    }

    @Test
    public void test2() {
        assertEquals("test@example.com", client.getEmail());
    }

    @Test
    public void test3() {
        client.setEmail("new@example.com");
        assertEquals("new@example.com", client.getEmail());
    }

    @Test
    public void test4() {
        assertEquals("Password123!", client.getPassword());
    }

    @Test
    public void test5() {
        client.setPassword("NewPass123!");
        assertEquals("NewPass123!", client.getPassword());
    }

    @Test
    public void test6() {
        assertTrue(client.getBookings().isEmpty());
    }

    @Test
    public void test7() {
        ArrayList<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking());
        client.setBookings(bookings);
        assertEquals(1, client.getBookings().size());
    }

    @Test
    public void test8() {
        assertTrue(Client.isValidEmail("valid@email.com"));
        assertFalse(Client.isValidEmail("invalid-email"));
    }

    @Test
    public void test9() {
        assertTrue(Client.isValidLicensePlate("ABC-123"));
        assertFalse(Client.isValidLicensePlate("INVALID!"));
    }

    @Test
    public void test10() {
        database.getAllClients().add(client);
        assertTrue(Client.authenticate("test@example.com", "Password123!"));
        assertFalse(Client.authenticate("wrong@example.com", "Password123!"));
    }

    @Test
    public void test11() {
        Booking booking = new Booking();
        Payment payment = new Payment();
        booking.setEndTime(LocalDateTime.now().plusHours(2));
        booking.setStartTime(LocalDateTime.now().plusMinutes(30));
        booking.setPayment(payment);
        payment.setIsRefunded(false);
        client.getBookings().add(booking);
        assertEquals(1, client.activeBookings().size());
    }

    @Test
    public void test12() {
        assertTrue(Client.isStrongPassword("Strong1!"));
        assertFalse(Client.isStrongPassword("weak"));
    }
    
    @Test
    public void test13() throws Exception {
        Client c = new Student("test@gmail.com", "1234", true);
        c.registerUser("student", "test@gmail.com", "1234");
        c = c.getClientByEmail("test@gmail.com");
    }
    
    @Test
    public void test14() {
        Client c = new Student("test@gmail.com", "1234", true);
        LocalDateTime start = LocalDateTime.now().plusDays(30);
        LocalDateTime end = LocalDateTime.now().plusDays(30).plusHours(2);
        ParkingSpace s = new ParkingSpace();
        Booking b = new Booking(3, c, 10, "AAA-111", start, end, payment, s, parkingLot);
        boolean select = c.selectSpace(b);
        LocalDateTime newStart = LocalDateTime.now().plusDays(32);
        LocalDateTime newEnd = LocalDateTime.now().plusDays(32).plusHours(2);
        LocalDateTime[] change = {newStart, newEnd};
        boolean cancel = c.updateParking("cancel", change, b);
        
        select = c.selectSpace(b);
        boolean edit = c.updateParking("edit", change, b);
        
        newStart = LocalDateTime.now().plusDays(30);
        newEnd = LocalDateTime.now().plusDays(30).plusHours(6);
        LocalDateTime[] change2 = {newStart, newEnd};
        select = c.selectSpace(b);
        boolean extend = c.updateParking("extend", change2, b);
        c = c.getClientByEmail("test@gmail.com");
        
    }
    
    @Test
    public void test15(){
        Client c = new Student("test@gmail.com", "1234", true);
        c = c.getClientByEmail("asdasdad");
        
    }
    
    @Test
    public void test16() {
        Client c = new Student("test@gmail.com", "1234", true);
        LocalDateTime start = LocalDateTime.now().plusDays(30);
        LocalDateTime end = LocalDateTime.now().plusDays(30).plusHours(2);
        ParkingSpace s = new ParkingSpace();
        parkingLot = new ParkingLot("2", "test", state, parkingSpaces, "test1");
        Booking b = new Booking(3, c, 10, "=/", start, end, payment, s, parkingLot);
        boolean select = c.selectSpace(b);
        
        b = new Booking(3, c, 10, "AAA-111", start, end, payment, s, parkingLot);
        select = c.selectSpace(b);
        
        
    }
    
    @Test
    public void test17() {
        ParkingSpace space = new ParkingSpace();
        space.setId(1);
        space.setEnabled(true);
        space.setOccupied(false);
        
        ParkingLot lot = new ParkingLot("1", "Test Lot", new EnabledState(), new ParkingSpace[10], "Location");
        lot.getParkingSpaces()[1] = space;
        
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(3);
        Booking booking = new Booking(1, client, 10.0, "INVALID!", start, end, null, space, lot);
        
        assertFalse(client.selectSpace(booking));
    }
    
    @Test
    public void test18() {
        ParkingSpace space = new ParkingSpace();
        space.setId(1);
        space.setEnabled(true);
        space.setOccupied(false);
        
        ParkingLot lot = new ParkingLot("1", "Test Lot", new DisabledState(), new ParkingSpace[10], "Location");
        lot.getParkingSpaces()[1] = space;
        
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(3);
        Booking booking = new Booking(1, client, 10.0, "ABC-123", start, end, null, space, lot);
    }
    
    @Test
    public void test19() {
        ParkingSpace space = new ParkingSpace();
        space.setId(1);
        space.setEnabled(true);
        space.setOccupied(true);
        
        ParkingLot lot = new ParkingLot("1", "Test Lot", new EnabledState(), new ParkingSpace[10], "Location");
        lot.getParkingSpaces()[1] = space;
        
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(3);
        Booking booking = new Booking(1, client, 10.0, "ABC-123", start, end, null, space, lot);
    }
    
    @Test
    public void test20() {
        ParkingSpace space = new ParkingSpace();
        space.setId(1);
        space.setEnabled(true);
        space.setOccupied(false);
        
        ParkingLot lot = new ParkingLot("1", "Test Lot", new EnabledState(), new ParkingSpace[10], "Location");
        lot.getParkingSpaces()[1] = space;
        
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(3);
        Booking booking = new Booking(1, client, 10.0, "ABC-123", start, end, null, space, lot);
        
        assertTrue(client.selectSpace(booking));
    }

    @Test
    public void test21() {
        LocalDateTime[] times = {LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(3)};
        Booking booking = new Booking();
        assertFalse(client.updateParking("invalid", times, booking));
    }

    @Test
    public void test22() {
        LocalDateTime[] times = {LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(3)};
        Booking booking = new Booking();
        assertFalse(client.updateParking("cancel", times, booking));
    }

    @Test
    public void test23() {
        ParkingSpace space = new ParkingSpace();
        space.setId(1);
        space.setEnabled(true);
        space.setOccupied(false);
        
        ParkingLot lot = new ParkingLot("1", "Test Lot", new EnabledState(), new ParkingSpace[10], "Location");
        lot.getParkingSpaces()[1] = space;
        
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(3);
        Booking booking = new Booking(1, client, 10.0, "ABC-123", start, end, new Payment(), space, lot);
        client.getBookings().add(booking);
        
        LocalDateTime[] times = {LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(3)};
        assertTrue(client.updateParking("cancel", times, booking));
        assertTrue(booking.getPayment().getIsRefunded());
        assertEquals(0, client.getBookings().size());
    }
    
    @Test
    public void test24() {
        ParkingSpace space = new ParkingSpace();
        space.setId(1);
        space.setEnabled(true);
        space.setOccupied(false);
        
        ParkingLot lot = new ParkingLot("1", "Test Lot", new EnabledState(), new ParkingSpace[10], "Location");
        lot.getParkingSpaces()[1] = space;
        
        LocalDateTime existingStart = LocalDateTime.now().plusHours(2);
        LocalDateTime existingEnd = LocalDateTime.now().plusHours(4);
        Booking existingBooking = new Booking(2, client, 10.0, "ABC-123", existingStart, existingEnd, null, space, lot);
        database.getAllBookings().add(existingBooking);
        
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(3);
        Booking booking = new Booking(1, client, 10.0, "ABC-123", start, end, null, space, lot);
        client.getBookings().add(booking);
        
        LocalDateTime[] times = {start, LocalDateTime.now().plusHours(5)};
        assertFalse(client.updateParking("extend", times, booking));
    }

    @Test
    public void test25() {
        ParkingSpace space = new ParkingSpace();
        space.setId(1);
        space.setEnabled(true);
        space.setOccupied(false);
        
        ParkingLot lot = new ParkingLot("1", "Test Lot", new EnabledState(), new ParkingSpace[10], "Location");
        lot.getParkingSpaces()[1] = space;
        
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(3);
        Booking booking = new Booking(1, client, 10.0, "ABC-123", start, end, null, space, lot);
        client.getBookings().add(booking);
        
        LocalDateTime[] newTimes = {LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(4)};
        assertTrue(client.updateParking("edit", newTimes, booking));
        assertEquals(newTimes[0], booking.getStartTime());
        assertEquals(newTimes[1], booking.getEndTime());
    }
    
}
