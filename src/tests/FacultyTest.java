package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import backend.*;

public class FacultyTest {

    private Faculty faculty;

    @Before
    public void setUp() throws Exception {
        faculty = new Faculty("testemail@example.com", "password123", true);
    }

    @Test
    public void test_1() {
        assertEquals("testemail@example.com", faculty.getEmail());
        assertEquals("password123", faculty.getPassword());
        assertTrue(faculty.getAccountApproved());
    }

    @Test
    public void test_2() {
        assertTrue(faculty.getAccountApproved());
        faculty.setAccountApproved(false);
        assertFalse(faculty.getAccountApproved());
    }

    @Test
    public void test_3() {
        faculty.setAccountApproved(false);
        assertFalse(faculty.getAccountApproved());
        faculty.setAccountApproved(true);
        assertTrue(faculty.getAccountApproved());
    }

    @Test
    public void test_4() {
        assertEquals(8.0, faculty.calculateDepositClient(), 0.01);
    }

    @Test
    public void test_5() {
        assertEquals("faculty", faculty.getClientType());
    }
    
    @Test
    public void test_6() {
        assertNotEquals("student", faculty.getClientType());
    }

    @Test
    public void test_7() {
        Faculty newfaculty = new Faculty("anotheremail@example.com", "password456", false);
        assertFalse(newfaculty.getAccountApproved());
        newfaculty.setAccountApproved(true);
        assertTrue(newfaculty.getAccountApproved());
    }

    @Test
    public void test_8() {
        assertEquals("testemail@example.com", faculty.getEmail());
    }

    @Test
    public void test_9() {
        assertEquals("password123", faculty.getPassword());
    }

    @Test
    public void test_10() {
        assertEquals(8, faculty.RATE);
    }
    
}