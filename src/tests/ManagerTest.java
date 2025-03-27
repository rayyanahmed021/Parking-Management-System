package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import backend.*;

public class ManagerTest {
    private Manager manager;
    private Database database;

    @Before
    public void setUp() {
    	manager = new Manager("admin", "password123");
        database = Database.getInstance();
        database.getAllParkingLots().clear();
        database.getAllManagers().clear();
        database.getAllManagers().add(manager);
    }

    @Test
    public void test_1() {
        assertEquals("admin", manager.getName());
        assertEquals("password123", manager.getPassword());
    }

    @Test
    public void test_2() {
        assertEquals("admin", manager.getName());
    }

    @Test
    public void test_3() {
        manager.setName("newAdmin");
        assertEquals("newAdmin", manager.getName());
    }

    @Test
    public void test_4() {
        assertEquals("password123", manager.getPassword());
    }

    @Test
    public void test_5() {
        manager.setPassword("newPassword");
        assertEquals("newPassword", manager.getPassword());
    }

    @Test
    public void test_6() {
        List<Manager> managers = new ArrayList<>();
        managers.add(new Manager("admin", "password123"));
        database.getAllManagers().clear();
        database.getAllManagers().addAll(managers);

        assertTrue(Manager.authenticate("admin", "password123"));
    }
    
    @Test
    public void test_7() {
        assertFalse(Manager.authenticate("admin", "wrongpassword"));
    }

    @Test
    public void test_8() {
        List<Manager> managers = new ArrayList<>();
        managers.add(new Manager("admin", "password123"));
        database.getAllManagers().clear();
        database.getAllManagers().addAll(managers);

        assertFalse(Manager.authenticate("wrongUser", "wrongPass"));
    }

    @Test
    public void test_9() {
        ParkingCommand command = new AddParkingLotCommand("A-123", "West", "Halifax");
        manager.executeCommand(command);
    }
 
    @Test
    public void test_10() {
        ParkingLot lot = new ParkingLot("lot1", "Lot A", new DisabledState(), new ParkingSpace[100], "Location A");
        database.getAllParkingLots().add(lot);

        UpdateParkingLotCommand command = new UpdateParkingLotCommand("enable", "lot1");
        manager.executeCommand(command);

        assertTrue(lot.getState() instanceof EnabledState);
    }

    @Test
    public void test_11() {
        ParkingLot lot = new ParkingLot("lot2", "Lot B", new EnabledState(), new ParkingSpace[100], "Location B");
        database.getAllParkingLots().add(lot);

        UpdateParkingLotCommand command = new UpdateParkingLotCommand("disable", "lot2");
        manager.executeCommand(command);

        assertTrue(lot.getState() instanceof DisabledState);
    }
    
    

}