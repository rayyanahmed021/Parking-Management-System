package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import backend.*;

public class ParkingLotTest {
    private ParkingLot parkingLot;
    private ParkingSpace[] parkingSpaces;
    private Database database;

    @Before
    public void setUp() {
        database = Database.getInstance();
        parkingSpaces = new ParkingSpace[100];
        
        parkingLot = new ParkingLot("PL123", "Test Lot", new EnabledState(), parkingSpaces, "123 Test St.");
        
        for (int i = 0; i < 100; i++) {
            parkingSpaces[i] = new ParkingSpace(i, parkingLot, true);
        }
        
        parkingLot.setParkingSpaces(parkingSpaces);
    }

    @Test
    public void testGetId() {
        assertEquals("PL123", parkingLot.getId());
    }

    @Test
    public void testSetId() {
        parkingLot.setId("PL456");
        assertEquals("PL456", parkingLot.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Test Lot", parkingLot.getName());
    }

    @Test
    public void testSetName() {
        parkingLot.setName("New Lot Name");
        assertEquals("New Lot Name", parkingLot.getName());
    }

    @Test
    public void testGetLocation() {
        assertEquals("123 Test St.", parkingLot.getLocation());
    }

    @Test
    public void testSetLocation() {
        parkingLot.setLocation("456 New St.");
        assertEquals("456 New St.", parkingLot.getLocation());
    }

    @Test
    public void testGetParkingSpaces() {
        assertEquals(100, parkingLot.getParkingSpaces().length);
    }

    @Test
    public void testSetParkingSpaces() {
        ParkingSpace[] newSpaces = new ParkingSpace[50];
        for (int i = 0; i < 50; i++) {
            newSpaces[i] = new ParkingSpace(i, parkingLot, true);
        }
        parkingLot.setParkingSpaces(newSpaces);
        assertEquals(50, parkingLot.getParkingSpaces().length);
    }

    @Test
    public void testSetState() {
        ParkingLotState newState = new EnabledState();
        parkingLot.setState(newState);
        assertEquals(newState, parkingLot.getState());
    }

    @Test
    public void testGetState() {
        assertTrue(parkingLot.getState() instanceof EnabledState);
    }

    // Test case to check state transition if such a method exists
    @Test
    public void testChangeState() {
        ParkingLotState newState = new DisabledState();
        parkingLot.setState(newState);
        assertTrue(parkingLot.getState() instanceof DisabledState);
    }

    // Test case to check parking lot with no spaces (edge case)
    @Test
    public void testNoParkingSpaces() {
        ParkingSpace[] emptySpaces = new ParkingSpace[0];
        parkingLot.setParkingSpaces(emptySpaces);
        assertEquals(0, parkingLot.getParkingSpaces().length);
    }

    // Test case to check if parking spaces are occupied properly
    @Test
    public void testParkingSpaceOccupied() {
        parkingSpaces[0].setOccupied(true);
        assertTrue(parkingSpaces[0].isOccupied());
    }

    // Test case to check if parking spaces are available properly
    @Test
    public void testParkingSpaceAvailable() {
        parkingSpaces[0].setOccupied(false);
        assertFalse(parkingSpaces[0].isOccupied());
    }

    // Test case to check adding more parking spaces (edge case)
    @Test
    public void testAddParkingSpaces() {
        ParkingSpace[] newSpaces = new ParkingSpace[150];
        for (int i = 0; i < 150; i++) {
            newSpaces[i] = new ParkingSpace(i, parkingLot, true);
        }
        parkingLot.setParkingSpaces(newSpaces);
        assertEquals(150, parkingLot.getParkingSpaces().length);
    }

    // Test case to ensure that state is not null
    @Test
    public void testStateNotNull() {
        assertNotNull(parkingLot.getState());
    }

    // Test case to verify the behavior of parking spaces after changing their states
    @Test
    public void testChangeParkingSpaceState() {
        ParkingSpace space = parkingSpaces[0];
        space.setOccupied(false);
        assertFalse(space.isOccupied());

        space.setOccupied(true);
        assertTrue(space.isOccupied());
    }

    // Test case to verify proper handling when changing the state to null (edge case)
//    @Test(expected = NullPointerException.class)
//    public void testSetStateToNull() {
//        parkingLot.setState(null);
//    }

    // Test case to verify invalid id setting (edge case)
    @Test
    public void testSetInvalidId() {
        parkingLot.setId("");
        assertEquals("", parkingLot.getId());
    }

    // Test case for verifying database connection (indirect test of Database class)
    @Test
    public void testDatabaseConnection() {
        assertNotNull(database);
    }
    
    @Test
    public void test() {
    	String id = this.parkingLot.randomIdGenerator();
    	assertNotNull(id);
    	
    }

    // Test case to verify the correct initial state of all parking spaces (edge case)
//    @Test
//    public void testInitialStateOfParkingSpaces() {
//        for (int i = 0; i < 100; i++) {
//            assertTrue(parkingSpaces[i].isOccupied());
//        }
//    }
}
