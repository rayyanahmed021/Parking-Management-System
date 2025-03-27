package tests;

import backend.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class DisabledStateTest {
    private DisabledState disabledState;
    private ParkingLot parkingLot;

    @Before
    public void setUp() {
        disabledState = new DisabledState();
        parkingLot = new ParkingLot("L1", "Test Lot", null, new ParkingSpace[10], "Test Location");
    }

    @Test
    public void test0() {
        assertFalse(disabledState.isEnabled());
    }

    @Test
    public void test1() {
        disabledState.handle(parkingLot);
    }

    @Test
    public void test2() {
        disabledState.handle(parkingLot);
    }

    @Test
    public void test3() {
        ParkingLot lot = new ParkingLot(null, null, null, null, null);
        disabledState.handle(lot);
    }

    @Test
    public void test4() {
        disabledState.handle(parkingLot);
        disabledState.handle(parkingLot);
    }

    @Test
    public void test5() {
        assertFalse(disabledState.isEnabled());
        assertFalse(disabledState.isEnabled());
        assertFalse(disabledState.isEnabled());
    }

    @Test
    public void test6() {
        ParkingLot lot1 = new ParkingLot("L1", "Lot 1", null, null, null);
        ParkingLot lot2 = new ParkingLot("L2", "Lot 2", null, null, null);
        
        disabledState.handle(lot1);
        disabledState.handle(lot2);
    }

    @Test
    public void test7() {
        ParkingLot lot = new ParkingLot("", "", null, null, null);
        disabledState.handle(lot);
    }

    @Test
    public void test8() {
        assertTrue(disabledState instanceof ParkingLotState);
    }

    @Test
    public void test10() {
        ParkingLot lot = new ParkingLot("L@1", "Lot $pecial!", null, null, null);
        disabledState.handle(lot);
    }

    @Test
    public void test11() {
        String longName = "A".repeat(1000);
        ParkingLot lot = new ParkingLot("L1", longName, null, null, null);
        disabledState.handle(lot);
    }

    @Test
    public void test12() {
        DisabledState state1 = new DisabledState();
        DisabledState state2 = new DisabledState();
        assertFalse(state1.isEnabled());
        assertFalse(state2.isEnabled());
    }

    @Test
    public void test13() {
        parkingLot.setState(new EnabledState());
        disabledState.handle(parkingLot);
    }

    @Test
    public void test14() {
        disabledState.handle(parkingLot);
    }
}