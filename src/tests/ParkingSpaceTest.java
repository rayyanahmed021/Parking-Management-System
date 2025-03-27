package tests;

import backend.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ParkingSpaceTest {
    private ParkingSpace parkingSpace;
    private ParkingLot parkingLot;

    @Before
    public void setUp() {
        parkingLot = new ParkingLot("L1", "North Lot", new EnabledState(), new ParkingSpace[10], "North Campus");
        parkingSpace = new ParkingSpace(1, parkingLot, true);
    }

    @Test
    public void test1() {
        assertEquals(1, parkingSpace.getId());
        assertEquals(parkingLot, parkingSpace.getParkingLot());
        assertFalse(parkingSpace.isOccupied());
        assertTrue(parkingSpace.isEnabled());
        assertNotNull(parkingSpace.getParkingSensor());
    }

    @Test
    public void test2() {
        ParkingSpace defaultSpace = new ParkingSpace();
        assertEquals(0, defaultSpace.getId());
        assertNull(defaultSpace.getParkingLot());
        assertFalse(defaultSpace.isOccupied());
        assertNotNull(defaultSpace.getParkingSensor());
    }

    @Test
    public void test3() {
        parkingSpace.setId(5);
        assertEquals(5, parkingSpace.getId());
    }

    @Test
    public void test4() {
        ParkingLot newLot = new ParkingLot("L2", "South Lot", new EnabledState(), new ParkingSpace[5], "South Campus");
        parkingSpace.setParkingLot(newLot);
        assertEquals(newLot, parkingSpace.getParkingLot());
    }

    @Test
    public void test5() {
        assertFalse(parkingSpace.isOccupied());
    }

    @Test
    public void test6() {
        parkingSpace.setOccupied(true);
        assertTrue(parkingSpace.isOccupied());
        
        parkingSpace.setOccupied(false);
        assertFalse(parkingSpace.isOccupied());
    }

    @Test
    public void test7() {
        parkingSpace.setEnabled(false);
        assertFalse(parkingSpace.isEnabled());
        
        parkingSpace.setEnabled(true);
        assertTrue(parkingSpace.isEnabled());
    }

    @Test
    public void test8() {
        assertNotNull(parkingSpace.getParkingSensor());
    }

    @Test
    public void test9() {
        ParkingSensor newSensor = new ParkingSensor(parkingSpace);
        parkingSpace.setParkingSensor(newSensor);
        assertEquals(newSensor, parkingSpace.getParkingSensor());
    }

    @Test
    public void test10() {
        assertFalse(parkingSpace.isOccupied());
        
        parkingSpace.update(1, "L1", true);
        assertTrue(parkingSpace.isOccupied());
        
        parkingSpace.update(2, "L1", false);
        assertFalse(parkingSpace.isOccupied());
        
        parkingSpace.update(1, "L2", true);
        assertTrue(parkingSpace.isOccupied());
    }

    @Test
    public void test11() {
        try {
            parkingSpace.setOccupied(true);
            parkingSpace.setOccupied(false);
        } catch (Exception e) {
            fail("setOccupied should not throw exceptions");
        }
    }

    @Test
    public void test12() {
        parkingSpace.setId(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, parkingSpace.getId());
        
        parkingSpace.setId(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, parkingSpace.getId());
        
        parkingSpace.setParkingLot(null);
        assertNull(parkingSpace.getParkingLot());
        
    }
}