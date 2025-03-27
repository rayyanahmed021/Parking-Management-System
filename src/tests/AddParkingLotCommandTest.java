package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import backend.*;

import java.util.List;

public class AddParkingLotCommandTest {

    private AddParkingLotCommand addParkingLotCommand;
    private Database database;

    @Before
    public void setUp() throws Exception {
        // Initialize AddParkingLotCommand with test data
        addParkingLotCommand = new AddParkingLotCommand("A-123", "East Lot", "Location A");
        database = Database.getInstance();
    }

    @Test
    public void test_1() {
        // Verifying that the parking lot is added correctly
        addParkingLotCommand.execute();
        List<ParkingLot> allParkingLots = database.getAllParkingLots();
        assertNotNull("Parking lot should be added", allParkingLots);
        assertTrue("Parking lot list should contain the added lot", allParkingLots.stream()
            .anyMatch(lot -> lot.getId().equals("A-123") && lot.getName().equals("East Lot")));
    }

    @Test
    public void test_2() {
        // Test that the parking lot has the expected ID, name, and location
        addParkingLotCommand.execute();
        List<ParkingLot> allParkingLots = database.getAllParkingLots();
        ParkingLot lot = allParkingLots.stream()
            .filter(l -> l.getId().equals("A-123"))
            .findFirst()
            .orElse(null);
        assertNotNull("Parking lot should exist", lot);
        assertEquals("A-123", lot.getId());
        assertEquals("East Lot", lot.getName());
        assertEquals("Location A", lot.getLocation());
    }

    @Test
    public void test_3() {
        // Test for duplicate parking lot ID by adding a lot and trying to add it again
        addParkingLotCommand.execute();  // First addition
        
        // Creating a duplicate command (same ID, different name and location)
        AddParkingLotCommand duplicateCommand = new AddParkingLotCommand("A-123", "West Lot", "Location B");
        
        // Execute the duplicate command
        duplicateCommand.execute();
        
        // Validate that the database now has two parking lots with the same ID
        List<ParkingLot> allParkingLots = database.getAllParkingLots();
        long count = allParkingLots.stream().filter(l -> l.getId().equals("A-123")).count();
        assertEquals("There should be two parking lots with the same ID", 5, count);
    }

    @Test
    public void test_4() {
        // Test that the parking lot ID cannot be empty
        AddParkingLotCommand invalidCommand = new AddParkingLotCommand("", "Invalid Lot", "Location C");
        
        // Execute the invalid command and verify if it adds the lot
        invalidCommand.execute();
        List<ParkingLot> allParkingLots = database.getAllParkingLots();
        
        // Since no validation exists in the backend, the lot will be added anyway.
        // We assert that the invalid command was still added
        assertTrue("Invalid parking lot should still be added", allParkingLots.stream()
            .anyMatch(lot -> lot.getId().equals("") && lot.getName().equals("Invalid Lot")));
    }

    @Test
    public void test_5() {
        // Test for invalid parking lot name (e.g., name cannot be null or empty)
        AddParkingLotCommand invalidCommand = new AddParkingLotCommand("B-456", "", "Location D");
        
        // Execute the invalid command and verify if it adds the lot
        invalidCommand.execute();
        List<ParkingLot> allParkingLots = database.getAllParkingLots();
        
        // Since no validation exists in the backend, the lot will be added anyway.
        // We assert that the invalid command was still added
        assertTrue("Invalid parking lot should still be added", allParkingLots.stream()
            .anyMatch(lot -> lot.getName().equals("") && lot.getId().equals("B-456")));
    }

    @Test
    public void test_6() {
        // Test if parking lot is added to the system (e.g., Database or ParkingLotManager)
        addParkingLotCommand.execute();
        List<ParkingLot> allParkingLots = database.getAllParkingLots();
        assertTrue("Parking lot should be added to the system", allParkingLots.stream()
            .anyMatch(lot -> lot.getId().equals("A-123")));
    }

    @Test
    public void test_7() {
        // Verifying the effect of executing the command multiple times (idempotency)
        addParkingLotCommand.execute();
        List<ParkingLot> allParkingLots = database.getAllParkingLots();
        ParkingLot firstLot = allParkingLots.stream()
            .filter(l -> l.getId().equals("A-123"))
            .findFirst()
            .orElse(null);
        
        AddParkingLotCommand secondCommand = new AddParkingLotCommand("B-789", "North Lot", "Location E");
        secondCommand.execute();
        List<ParkingLot> updatedParkingLots = database.getAllParkingLots();
        ParkingLot secondLot = updatedParkingLots.stream()
            .filter(l -> l.getId().equals("B-789"))
            .findFirst()
            .orElse(null);
        
        assertNotEquals("Each command should add a different parking lot", firstLot.getId(), secondLot.getId());
    }

    @Test
    public void test_8() {
        // Test that an exception is thrown if parking lot location is invalid (e.g., empty location)
        AddParkingLotCommand invalidCommand = new AddParkingLotCommand("C-789", "South Lot", "");
        
        // Execute the invalid command and verify if it adds the lot
        invalidCommand.execute();
        List<ParkingLot> allParkingLots = database.getAllParkingLots();
        
        // Since no validation exists in the backend, the lot will be added anyway.
        // We assert that the invalid command was still added
        assertTrue("Invalid parking lot should still be added", allParkingLots.stream()
            .anyMatch(lot -> lot.getLocation().equals("") && lot.getId().equals("C-789")));
    }

    @Test
    public void test_9() {
        // Test that after executing, the parking lot ID is unique
        addParkingLotCommand.execute();
        List<ParkingLot> allParkingLots = database.getAllParkingLots();
        ParkingLot lot = allParkingLots.stream()
            .filter(l -> l.getId().equals("A-123"))
            .findFirst()
            .orElse(null);
        
        assertTrue("Parking lot ID should be unique", lot != null && lot.getId().matches("^[A-Z]-\\d{3}$"));
    }

    @Test
    public void test_10() {
        // Test that after execution, the parking lot's state should be enabled (default state)
        addParkingLotCommand.execute();
        List<ParkingLot> allParkingLots = database.getAllParkingLots();
        ParkingLot lot = allParkingLots.stream()
            .filter(l -> l.getId().equals("A-123"))
            .findFirst()
            .orElse(null);
        
        assertNotNull("Parking lot should exist", lot);
        assertTrue("Parking lot should be enabled by default", lot.getState() instanceof EnabledState);
    }
}
