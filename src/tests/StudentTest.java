package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import backend.*;

public class StudentTest {

    private Student student;

    @Before
    public void setUp() throws Exception {
        student = new Student("testemail@example.com", "password123", true);
    }

    @Test
    public void test_1() {
        assertEquals("testemail@example.com", student.getEmail());
        assertEquals("password123", student.getPassword());
        assertTrue(student.getAccountApproved());
    }

    @Test
    public void test_2() {
        assertTrue(student.getAccountApproved());
        student.setAccountApproved(false);
        assertFalse(student.getAccountApproved());
    }

    @Test
    public void test_3() {
        student.setAccountApproved(false);
        assertFalse(student.getAccountApproved());
        student.setAccountApproved(true);
        assertTrue(student.getAccountApproved());
    }

    @Test
    public void test_4() {
        assertEquals(5.0, student.calculateDepositClient(), 0.01);
    }

    @Test
    public void test_5() {
        assertEquals("student", student.getClientType());
    }
    
    @Test
    public void test_6() {
        assertNotEquals("faculty", student.getClientType());
    }

    @Test
    public void test_7() {
        Student newStudent = new Student("anotheremail@example.com", "password456", false);
        assertFalse(newStudent.getAccountApproved());
        newStudent.setAccountApproved(true);
        assertTrue(newStudent.getAccountApproved());
    }

    @Test
    public void test_8() {
        assertEquals("testemail@example.com", student.getEmail());
    }

    @Test
    public void test_9() {
        assertEquals("password123", student.getPassword());
    }

    @Test
    public void test_10() {
        assertEquals(5, Student.RATE);
    }
    
}