package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import backend.*;

public class PayPalStrategyTest {

    private PayPalStrategy payPalStrategy;

    @Before
    public void setUp() throws Exception {
        payPalStrategy = new PayPalStrategy("johndoe@example.com", "password123");
    }

    @Test
    public void test_1() {
        assertEquals("johndoe@example.com", payPalStrategy.getUsername());
    }

    @Test
    public void test_2() {
        assertEquals("password123", payPalStrategy.getPassword());
    }

    @Test
    public void test_3() {
        Payment payment = payPalStrategy.processPayment(150.0);
        assertNotNull(payment);
        assertEquals(150.0, payment.getTotal(), 0.01);
        assertFalse(payment.getIsRefunded());
    }

    @Test
    public void test_4() {
        payPalStrategy.setUsername("janedoe@example.com");
        assertEquals("janedoe@example.com", payPalStrategy.getUsername());
    }

    @Test
    public void test_5() {
        payPalStrategy.setPassword("newpassword123");
        assertEquals("newpassword123", payPalStrategy.getPassword());
    }

    @Test
    public void test_6() {
        Payment payment1 = payPalStrategy.processPayment(200.0);
        assertEquals(200.0, payment1.getTotal(), 0.01);
        
        Payment payment2 = payPalStrategy.processPayment(350.0);
        assertEquals(350.0, payment2.getTotal(), 0.01);
    }

    @Test
    public void test_7() {
        Payment payment = new Payment(150.0, false, payPalStrategy);
        assertEquals("PayPal", payment.getPaymentMethod());
    }

    @Test
    public void test_8() {
        payPalStrategy.setPassword("securepassword456");
        assertEquals("securepassword456", payPalStrategy.getPassword());
    }

    @Test
    public void test_9() {
        payPalStrategy.setUsername("updatedemail@example.com");
        assertEquals("updatedemail@example.com", payPalStrategy.getUsername());
    }

    @Test
    public void test_10() {
        Payment payment = payPalStrategy.processPayment(50.0);
        assertNotNull(payment);
        assertEquals(50.0, payment.getTotal(), 0.01);
        assertFalse(payment.getIsRefunded());
    }
}
