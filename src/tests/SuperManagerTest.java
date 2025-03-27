package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import backend.*;

public class SuperManagerTest {
    private SuperManager superManager;
    private Database database;

    @Before
    public void setUp() {
        database = Database.getInstance();
        database.getAllManagers().clear();
        superManager = SuperManager.getSuperManagerInstance("superadmin", "superpassword");
        database.getAllManagers().add(superManager);
    }

    @Test
    public void test_1() {
        SuperManager instance1 = SuperManager.getSuperManagerInstance("superadmin", "superpassword");
        SuperManager instance2 = SuperManager.getSuperManagerInstance("otheradmin", "otherpassword");
        assertSame(instance1, instance2);
    }

    @Test
    public void test_2() {
        String password = SuperManager.generateStrongPassword(12);
        assertNotNull(password);
        assertEquals(12, password.length());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_3() {
    	// we wanted passwords to be of length 8 or more
        SuperManager.generateStrongPassword(6);
    }

    @Test
    public void test_4() {
        String username = SuperManager.generateUsername("John", "Doe");
        assertTrue(username.matches("jd\\d{1,4}"));
    }

    @Test
    public void test_5() {
        String uniqueUsername = SuperManager.generateUniqueUsername("John", "Doe", database);
        assertNotNull(uniqueUsername);
        for (Manager m : database.getAllManagers()) {
            assertNotEquals(uniqueUsername, m.getName());
        }
    }

    @Test
    public void test_6() {
        String[] account = superManager.createManagerAccount("Alice", "Smith");
        assertNotNull(account);
        assertEquals(2, account.length);
        assertNotNull(account[0]);
        assertNotNull(account[1]);
    }

    @Test
    public void test_7() {
        assertTrue(SuperManager.authenticate("superadmin", "superpassword"));
    }

    @Test
    public void test_8() {
        Manager manager = new Manager("manager1", "password1");
        database.getAllManagers().add(manager);
        assertFalse(SuperManager.authenticate("manager1", "password1"));
    }

    @Test
    public void test_9() {
        assertFalse(SuperManager.authenticate("wrongUser", "wrongPass"));
    }

    @Test
    public void test_10() {
        assertEquals("SuperManager Name: superadmin", superManager.getSuperManagerData());
    }
    
    @Test
    public void test_11() {
        ParkingCommand command = new AddParkingLotCommand("A-123", "West", "Halifax");
        superManager.executeCommand(command);
    }
    
}
