package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import backend.*;

public class VisitorTest {

	private Visitor visitor;

    @Before
    public void setUp() throws Exception {
        visitor = new Visitor("visitor@example.com", "visitorpassword");
    }

    @Test
    public void test_1() {
        assertEquals("visitor@example.com", visitor.getEmail());
        assertEquals("visitorpassword", visitor.getPassword());
    }

    @Test
    public void test_2() {
        assertEquals(15.0, visitor.calculateDepositClient(), 0.01);
    }

    @Test
    public void test_3() {
        assertEquals("visitor", visitor.getClientType());
    }

    @Test
    public void test_4() {
        assertEquals("visitor@example.com", visitor.getEmail());
    }

    @Test
    public void test_5() {
        assertEquals("visitorpassword", visitor.getPassword());
    }

    @Test
    public void test_6() {
        assertEquals(15, Visitor.RATE);
    }

    @Test
    public void test_7() {
        Visitor anotherVisitor = new Visitor("visitor2@example.com", "123");
        assertEquals(15.0, anotherVisitor.calculateDepositClient(), 0.01);
    }

    @Test
    public void test_8() {
        Visitor anotherVisitor = new Visitor("visitor3@example.com", "password123");
        assertEquals("visitor", anotherVisitor.getClientType());
    }

    @Test
    public void test_9() {
        Visitor visitor1 = new Visitor("rah@gail.com", "12345");
        Visitor visitor2 = new Visitor("rayyan@gmail.com", "54321");

        assertNotEquals(visitor1.getEmail(), visitor2.getEmail());
        assertNotEquals(visitor1.getPassword(), visitor2.getPassword());
    }

    @Test
    public void test_10() {
        assertNotEquals("student", visitor.getClientType());
    }
    
}