package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import backend.*;

import java.util.List;

public class UpdateParkingSpaceCommandTest {

    private UpdateParkingSpaceCommand updateParkingSpaceCommand;
    private Database database;

    @Before
    public void setUp() throws Exception {
        ParkingLot lot = new ParkingLot("A-123", "East Lot", new EnabledState(), new ParkingSpace[100], "Location A");
        for (int i = 0; i < 100; i++) {
            lot.getParkingSpaces()[i] = new ParkingSpace(i, lot, true);
        }
        Database db = Database.getInstance();
        db.getAllParkingLots().add(lot);
        updateParkingSpaceCommand = new UpdateParkingSpaceCommand("enable", 0, "A-123");
        database = Database.getInstance();
    }

    @Test
    public void test_1() {
        updateParkingSpaceCommand.execute();
        
        ParkingLot lot = database.getAllParkingLots().stream()
            .filter(l -> l.getId().equals("A-123"))
            .findFirst()
            .orElse(null);
        
        assertNotNull("Parking lot should exist", lot);
        ParkingSpace space = lot.getParkingSpaces()[0];
        assertTrue("Parking space should be enabled", space.isEnabled());
    }

    @Test
    public void test_2() {
        updateParkingSpaceCommand = new UpdateParkingSpaceCommand("disable", 0, "A-123");
        updateParkingSpaceCommand.execute();
        
        ParkingLot lot = database.getAllParkingLots().stream()
            .filter(l -> l.getId().equals("A-123"))
            .findFirst()
            .orElse(null);
        
        assertNotNull("Parking lot should exist", lot);
    }

    @Test
    public void test_3() {
        updateParkingSpaceCommand.execute();
        ParkingLot lot = database.getAllParkingLots().stream()
            .filter(l -> l.getId().equals("A-123"))
            .findFirst()
            .orElse(null);
        
        assertNotNull("Parking lot should exist", lot);
        ParkingSpace space = lot.getParkingSpaces()[0];
        assertTrue("Parking space should already be enabled", space.isEnabled());

        UpdateParkingSpaceCommand reEnableCommand = new UpdateParkingSpaceCommand("enable", 0, "A-123");
        reEnableCommand.execute();
        
        space = lot.getParkingSpaces()[0];
        assertTrue("Parking space state should remain enabled", space.isEnabled());
    }

    @Test
    public void test_4() {
        updateParkingSpaceCommand = new UpdateParkingSpaceCommand("disable", 0, "A-123");
        updateParkingSpaceCommand.execute();

        ParkingLot lot = database.getAllParkingLots().stream()
            .filter(l -> l.getId().equals("A-123"))
            .findFirst()
            .orElse(null);
        
        assertNotNull("Parking lot should exist", lot);
    }

    @Test
    public void test_5() {
        UpdateParkingSpaceCommand invalidSpaceCommand = new UpdateParkingSpaceCommand("enable", 99, "A-123");
        invalidSpaceCommand.execute();
        
        ParkingLot lot = database.getAllParkingLots().stream()
            .filter(l -> l.getId().equals("A-123"))
            .findFirst()
            .orElse(null);
        
        assertNotNull("Parking lot should exist", lot);
    }

    @Test
    public void test_6() {
    	ParkingLot lot = database.getAllParkingLots().stream()
                .filter(l -> l.getId().equals("A-123"))
                .findFirst()
                .orElse(null);
            
            assertNotNull("Parking lot should exist", lot);
            ParkingSpace space = lot.getParkingSpaces()[1];
            assertTrue("Parking space should be enabled initially", space.isEnabled());
    }

    @Test
    public void test_7() {
        ParkingLot lot = database.getAllParkingLots().stream()
            .filter(l -> l.getId().equals("A-123"))
            .findFirst()
            .orElse(null);
        
        assertNotNull("Parking lot should exist", lot);
        ParkingSpace space = lot.getParkingSpaces()[0];
        assertTrue("Parking space should be enabled initially", space.isEnabled());
    }

    @Test
    public void test_8() {
        updateParkingSpaceCommand = new UpdateParkingSpaceCommand("disable", 0, "A-123");
        updateParkingSpaceCommand.execute();

        ParkingLot lot = database.getAllParkingLots().stream()
            .filter(l -> l.getId().equals("A-123"))
            .findFirst()
            .orElse(null);
        
        ParkingSpace space = lot.getParkingSpaces()[0];
        UpdateParkingSpaceCommand enableCommand = new UpdateParkingSpaceCommand("enable", 0, "A-123");
        enableCommand.execute();
        
        space = lot.getParkingSpaces()[0];
        assertTrue("Parking space should be enabled after re-enable", space.isEnabled());
    }

    @Test
    public void test_9() {
        UpdateParkingSpaceCommand disableCommand = new UpdateParkingSpaceCommand("disable", 0, "A-123");
        disableCommand.execute();
        
        ParkingLot lot = database.getAllParkingLots().stream()
            .filter(l -> l.getId().equals("A-123"))
            .findFirst()
            .orElse(null);
        
        assertNotNull("Parking lot should exist", lot);
    }

    @Test
    public void test_10() {
        UpdateParkingSpaceCommand enableCommand = new UpdateParkingSpaceCommand("enable", 0, "A-123");
        enableCommand.execute();
        
        ParkingLot lot = database.getAllParkingLots().stream()
            .filter(l -> l.getId().equals("A-123"))
            .findFirst()
            .orElse(null);
        
        ParkingSpace space = lot.getParkingSpaces()[0];
        assertNotNull("Parking space should exist", space);
        assertTrue("Parking space should be enabled in the database", space.isEnabled());
    }
}
