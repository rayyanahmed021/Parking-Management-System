package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import backend.*;

import java.util.List;

public class UpdateParkingLotCommandTest {

    private UpdateParkingLotCommand updateParkingLotCommand, updateParkingLotCommand1;
    private Database database;

    @Before
    public void setUp() throws Exception {
        AddParkingLotCommand addParkingLotCommand = new AddParkingLotCommand("A-123", "East Lot", "Location A");
        AddParkingLotCommand addParkingLotCommand1 = new AddParkingLotCommand("A-987", "West Lot", "Location B");
        addParkingLotCommand.execute();  // First add a parking lot to the database
        addParkingLotCommand1.execute();
        
        updateParkingLotCommand = new UpdateParkingLotCommand("enable", "A-123");
        updateParkingLotCommand1 = new UpdateParkingLotCommand("enable", "A-987");
        database = Database.getInstance();
    }

    @Test
    public void test_1() {
        updateParkingLotCommand.execute();
        List<ParkingLot> allParkingLots = database.getAllParkingLots();
        ParkingLot lot = allParkingLots.stream()
            .filter(l -> l.getId().equals("A-123"))
            .findFirst()
            .orElse(null);

        assertNotNull("Parking lot should exist", lot);
        assertTrue("Parking lot should be enabled after update", lot.getState() instanceof EnabledState);
    }

    @Test
    public void test_2() {
        updateParkingLotCommand1.execute();
        List<ParkingLot> allParkingLots = database.getAllParkingLots();
        ParkingLot lot = allParkingLots.stream()
            .filter(l -> l.getId().equals("A-987"))
            .findFirst()
            .orElse(null);

        assertNotNull("Parking lot should exist", lot);
        assertTrue("Parking lot should be enabled after update", lot.getState() instanceof EnabledState);
    }
   
    @Test
    public void test_3() {
        updateParkingLotCommand.execute(); 
        ParkingLot lot = database.getAllParkingLots().stream()
            .filter(l -> l.getId().equals("A-123"))
            .findFirst()
            .orElse(null);

        assertNotNull("Parking lot should exist", lot);
        assertTrue("Parking lot should already be enabled", lot.getState() instanceof EnabledState);

        UpdateParkingLotCommand reEnableCommand = new UpdateParkingLotCommand("enable", "A-123");
        reEnableCommand.execute();
        
        lot = database.getAllParkingLots().stream()
            .filter(l -> l.getId().equals("A-123"))
            .findFirst()
            .orElse(null);

        assertTrue("Parking lot state should remain enabled", lot.getState() instanceof EnabledState);
    }
    
    @Test
    public void test_4() {
        updateParkingLotCommand1.execute();  
        ParkingLot lot = database.getAllParkingLots().stream()
            .filter(l -> l.getId().equals("A-987"))
            .findFirst()
            .orElse(null);

        assertNotNull("Parking lot should exist", lot);
        assertTrue("Parking lot should already be enabled", lot.getState() instanceof EnabledState);

        UpdateParkingLotCommand reEnableCommand = new UpdateParkingLotCommand("enable", "A-987");
        reEnableCommand.execute();
        
        lot = database.getAllParkingLots().stream()
            .filter(l -> l.getId().equals("A-987"))
            .findFirst()
            .orElse(null);

        assertTrue("Parking lot state should remain enabled", lot.getState() instanceof EnabledState);
    }
    
    @Test
    public void test_5() {
        List<ParkingLot> allParkingLots = database.getAllParkingLots();
        ParkingLot lot = allParkingLots.stream()
            .filter(l -> l.getId().equals("A-987"))
            .findFirst()
            .orElse(null);
        
        assertNotNull("Parking lot should exist", lot);
        assertTrue("Parking lot state should be the initial state", lot.getState() instanceof EnabledState);
    }

    @Test
    public void test_6() {
        List<ParkingLot> allParkingLots = database.getAllParkingLots();
        ParkingLot lot = allParkingLots.stream()
            .filter(l -> l.getId().equals("A-123"))
            .findFirst()
            .orElse(null);
        
        assertNotNull("Parking lot should exist", lot);
        assertTrue("Parking lot state should be the initial state", lot.getState() instanceof EnabledState);
    }

    @Test
    public void test_7() {
        updateParkingLotCommand = new UpdateParkingLotCommand("disable", "A-123");
        updateParkingLotCommand.execute();
        
        UpdateParkingLotCommand reEnableCommand = new UpdateParkingLotCommand("enable", "A-123");
        reEnableCommand.execute();
        
        List<ParkingLot> allParkingLots = database.getAllParkingLots();
        ParkingLot lot = allParkingLots.stream()
            .filter(l -> l.getId().equals("A-123"))
            .findFirst()
            .orElse(null);

        assertNotNull("Parking lot should exist", lot);
        assertTrue("Parking lot should be enabled after update", lot.getState() instanceof EnabledState);
    }

    @Test
    public void test_8() {
        UpdateParkingLotCommand updateLocationCommand = new UpdateParkingLotCommand("updateLocation", "A-123");
        updateLocationCommand.execute();
        
        List<ParkingLot> allParkingLots = database.getAllParkingLots();
        ParkingLot lot = allParkingLots.stream()
            .filter(l -> l.getId().equals("A-123"))
            .findFirst()
            .orElse(null);

        assertNotNull("Parking lot should exist", lot);
        assertEquals("Location should not change", "Location A", lot.getLocation());
    }

    @Test
    public void test_9() {
        UpdateParkingLotCommand updateNameCommand = new UpdateParkingLotCommand("updateName", "A-123");
        updateNameCommand.execute();
        
        List<ParkingLot> allParkingLots = database.getAllParkingLots();
        ParkingLot lot = allParkingLots.stream()
            .filter(l -> l.getId().equals("A-123"))
            .findFirst()
            .orElse(null);

        assertNotNull("Parking lot should exist", lot);
        assertEquals("Name should not change", "East Lot", lot.getName());
    }
    
    @Test
    public void test_10() {
        UpdateParkingLotCommand updateNameCommand = new UpdateParkingLotCommand("updateName1", "A-987");
        updateNameCommand.execute();
        
        List<ParkingLot> allParkingLots = database.getAllParkingLots();
        ParkingLot lot = allParkingLots.stream()
            .filter(l -> l.getId().equals("A-987"))
            .findFirst()
            .orElse(null);

        assertNotNull("Parking lot should exist", lot);
        assertEquals("Name should not change", "West Lot", lot.getName());
    }
}
