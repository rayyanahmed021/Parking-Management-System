package tests;

import backend.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ParkingSensorTest {
    private ParkingSensor parkingSensor;
    private ParkingSpace parkingSpace;
    private ParkingLot parkingLot;
    private Car testCar;
    private boolean observerNotified;
    private int notifiedSpaceId;
    private String notifiedLotId;
    private boolean notifiedOccupiedStatus;

    @Before
    public void setUp() {
        parkingLot = new ParkingLot("L1", "North Lot", new EnabledState(), new ParkingSpace[10], "North Campus");
        parkingSpace = new ParkingSpace(1, parkingLot, true);
        parkingSensor = new ParkingSensor(parkingSpace);
        testCar = new Car("ABC123", "Toyota", "Camry", "Sedan");
        resetObserverFlags();
    }

    private void resetObserverFlags() {
        observerNotified = false;
        notifiedSpaceId = -1;
        notifiedLotId = null;
        notifiedOccupiedStatus = false;
    }

    @Test
    public void test1() {
        assertNotNull(parkingSensor);
        assertNull(parkingSensor.getCarInfo());
    }

    @Test
    public void test2() {
        parkingSpace.setOccupied(false);
        assertTrue(parkingSensor.checkSpaceAvailable());
    }

    @Test
    public void test3() {
        parkingSpace.setOccupied(true);
        assertFalse(parkingSensor.checkSpaceAvailable());
    }

    @Test
    public void test4() {
        parkingSensor.setCarInfo(testCar);
        assertEquals(testCar, parkingSensor.getCarInfo());
    }

    @Test
    public void test5() {
        parkingSensor.addObserver((spaceID, lotId, isOccupied) -> {
            observerNotified = true;
            notifiedSpaceId = spaceID;
            notifiedLotId = lotId;
            notifiedOccupiedStatus = isOccupied;
        });
        
        parkingSpace.setOccupied(true);
        parkingSensor.notifyObservers();
        
        assertTrue(observerNotified);
        assertEquals(parkingSpace.getId(), notifiedSpaceId);
        assertEquals(parkingLot.getId(), notifiedLotId);
        assertTrue(notifiedOccupiedStatus);
    }

    @Test
    public void test6() {
        ParkingObserver observer = (spaceID, lotId, isOccupied) -> {
            observerNotified = true;
        };
        
        parkingSensor.addObserver(observer);
        parkingSensor.removeObserver(observer);
        
        resetObserverFlags();
        parkingSpace.setOccupied(true);
        parkingSensor.notifyObservers();
        
        assertFalse(observerNotified);
    }

    @Test
    public void test7() {
        parkingSensor.notifyObservers();
        assertFalse(observerNotified);
    }

    @Test
    public void test8() {
        parkingSensor.addObserver((spaceID, lotId, isOccupied) -> {
            observerNotified = true;
            notifiedSpaceId = spaceID;
            notifiedOccupiedStatus = isOccupied;
        });
        
        parkingSpace.setOccupied(true);
        parkingSensor.notifyObservers();
        
        assertTrue(observerNotified);
        assertEquals(parkingSpace.getId(), notifiedSpaceId);
        assertTrue(notifiedOccupiedStatus);
    }

    @Test
    public void test9() {
        final boolean[] firstObserverNotified = {false};
        final boolean[] secondObserverNotified = {false};
        
        parkingSensor.addObserver((spaceID, lotId, isOccupied) -> {
            firstObserverNotified[0] = true;
        });
        
        parkingSensor.addObserver((spaceID, lotId, isOccupied) -> {
            secondObserverNotified[0] = true;
        });
        
        parkingSpace.setOccupied(false);
        parkingSensor.notifyObservers();
        
        assertTrue(firstObserverNotified[0]);
        assertTrue(secondObserverNotified[0]);
    }
    
    @Test
    public void test10() {
        ParkingObserver observer = (spaceID, lotId, isOccupied) -> {
            observerNotified = true;
        };
        
        parkingSensor.addObserver(observer);
        parkingSensor.addObserver(observer);
        
        parkingSpace.setOccupied(true);
        parkingSensor.notifyObservers();
        
        assertTrue(observerNotified);
    }

    @Test
    public void test11() {
        ParkingObserver observer = (spaceID, lotId, isOccupied) -> {
            observerNotified = true;
        };
        
        parkingSensor.removeObserver(observer);
        
        parkingSpace.setOccupied(true);
        parkingSensor.notifyObservers();
        
        assertFalse(observerNotified);
    }

    @Test
    public void test12() {
        parkingSensor.addObserver((spaceID, lotId, isOccupied) -> {
            observerNotified = true;
            notifiedLotId = lotId;
        });
        
        parkingSpace.setOccupied(true);
        parkingSensor.notifyObservers();
        
        assertTrue(observerNotified);
        assertEquals(parkingLot.getId(), notifiedLotId);
    }

    @Test
    public void test13() {
        parkingSensor.setCarInfo(null);
        assertNull(parkingSensor.getCarInfo());
    }

    @Test
    public void test14() {
        parkingSensor.addObserver((spaceID, lotId, isOccupied) -> {
            notifiedOccupiedStatus = isOccupied;
        });
        
        parkingSpace.setOccupied(true);
        parkingSensor.notifyObservers();
        assertTrue(notifiedOccupiedStatus);
        
        parkingSpace.setOccupied(false);
        parkingSensor.notifyObservers();
        assertFalse(notifiedOccupiedStatus);
    }

    @Test
    public void test16() {
        final int[] notificationCount = {0};
        
        parkingSensor.addObserver((spaceID, lotId, isOccupied) -> {
            notificationCount[0]++;
        });
        
        parkingSpace.setOccupied(true);
        parkingSensor.notifyObservers();
        
        parkingSpace.setOccupied(false);
        parkingSensor.notifyObservers();
        
        assertEquals(2, notificationCount[0]);
    }
}