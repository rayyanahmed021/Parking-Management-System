package tests;

import backend.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class EnabledStateTest {
    private EnabledState enabledState;
    private ParkingLot parkingLot;

    @Before
    public void setUp() {
        enabledState = new EnabledState();
        parkingLot = new ParkingLot("L1", "Test Lot", null, new ParkingSpace[10], "Test Location");
    }

    @Test
    public void test0() {
        assertTrue(enabledState.isEnabled());
    }

    @Test
    public void test1() {
        enabledState.handle(parkingLot);
    }

    @Test
    public void test2() {
        enabledState.handle(parkingLot);
    }

    @Test
    public void test3() {
        ParkingLot lot = new ParkingLot(null, null, null, null, null);
        enabledState.handle(lot);
    }

    @Test
    public void test4() {
        enabledState.handle(parkingLot);
        enabledState.handle(parkingLot);
    }

    @Test
    public void test5() {
        assertTrue(enabledState.isEnabled());
        assertTrue(enabledState.isEnabled());
        assertTrue(enabledState.isEnabled());
    }

    @Test
    public void test6() {
        ParkingLot lot1 = new ParkingLot("L1", "Lot 1", null, null, null);
        ParkingLot lot2 = new ParkingLot("L2", "Lot 2", null, null, null);
        
        enabledState.handle(lot1);
        enabledState.handle(lot2);
    }

    @Test
    public void test7() {
        ParkingLot lot = new ParkingLot("", "", null, null, null);
        enabledState.handle(lot);
    }

    @Test
    public void test8() {
        assertTrue(enabledState instanceof ParkingLotState);
    }

    @Test
    public void test10() {
        ParkingLot lot = new ParkingLot("L@1", "Lot $pecial!", null, null, null);
        enabledState.handle(lot);
    }

    @Test
    public void test11() {
        String longName = "A".repeat(1000);
        ParkingLot lot = new ParkingLot("L1", longName, null, null, null);
        enabledState.handle(lot);
    }

    @Test
    public void test12() {
        EnabledState state1 = new EnabledState();
        EnabledState state2 = new EnabledState();
        assertTrue(state1.isEnabled());
        assertTrue(state2.isEnabled());
    }

    @Test
    public void test13() {
        parkingLot.setState(new DisabledState());
        enabledState.handle(parkingLot);
    }

    @Test
    public void test14() {
        enabledState.handle(parkingLot);
    }
}