package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import backend.*;

public class NonFacultyTest {

    private NonFaculty nonFaculty;

    @Before
    public void setUp() throws Exception {
        nonFaculty = new NonFaculty("testemail@example.com", "password123", true);
    }

    @Test
    public void test_1() {
        assertEquals("testemail@example.com", nonFaculty.getEmail());
        assertEquals("password123", nonFaculty.getPassword());
        assertTrue(nonFaculty.getAccountApproved());
    }

    @Test
    public void test_2() {
        assertTrue(nonFaculty.getAccountApproved());
        nonFaculty.setAccountApproved(false);
        assertFalse(nonFaculty.getAccountApproved());
    }

    @Test
    public void test_3() {
        nonFaculty.setAccountApproved(false);
        assertFalse(nonFaculty.getAccountApproved());
        nonFaculty.setAccountApproved(true);
        assertTrue(nonFaculty.getAccountApproved());
    }

    @Test
    public void test_4() {
        assertEquals(10.0, nonFaculty.calculateDepositClient(), 0.01);
    }

    @Test
    public void test_5() {
        assertEquals("nonfaculty", nonFaculty.getClientType());
    }
    
    @Test
    public void test_6() {
        assertNotEquals("faculty", nonFaculty.getClientType());
    }

    @Test
    public void test_7() {
        NonFaculty newnonFaculty = new NonFaculty("anotheremail@example.com", "password456", false);
        assertFalse(newnonFaculty.getAccountApproved());
        newnonFaculty.setAccountApproved(true);
        assertTrue(newnonFaculty.getAccountApproved());
    }

    @Test
    public void test_8() {
        assertEquals("testemail@example.com", nonFaculty.getEmail());
    }

    @Test
    public void test_9() {
        assertEquals("password123", nonFaculty.getPassword());
    }

    @Test
    public void test_10() {
        assertEquals(10, nonFaculty.RATE);
    }
    
}