package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import backend.*;

public class MobilePaymentStrategyTest {

    private MobilePaymentStrategy mobilePaymentStrategy;

    @Before
    public void setUp() throws Exception {
        mobilePaymentStrategy = new MobilePaymentStrategy("1234567890", "Verizon");
    }

    @Test
    public void test_1() {
        assertEquals("1234567890", mobilePaymentStrategy.getMobileNumber());
    }

    @Test
    public void test_2() {
        assertEquals("Verizon", mobilePaymentStrategy.getProvider());
    }

    @Test
    public void test_3() {
        Payment payment = mobilePaymentStrategy.processPayment(100.0);
        assertNotNull(payment);
        assertEquals(100.0, payment.getTotal(), 0.01);
        assertFalse(payment.getIsRefunded());
    }

    @Test
    public void test_4() {
        mobilePaymentStrategy.setMobileNumber("0987654321");
        assertEquals("0987654321", mobilePaymentStrategy.getMobileNumber());
    }

    @Test
    public void test_5() {
        mobilePaymentStrategy.setProvider("T-Mobile");
        assertEquals("T-Mobile", mobilePaymentStrategy.getProvider());
    }

    @Test
    public void test_6() {
        Payment payment1 = mobilePaymentStrategy.processPayment(200.0);
        assertEquals(200.0, payment1.getTotal(), 0.01);
        
        Payment payment2 = mobilePaymentStrategy.processPayment(350.0);
        assertEquals(350.0, payment2.getTotal(), 0.01);
    }

    @Test
    public void test_7() {
        Payment payment = new Payment(150.0, false, mobilePaymentStrategy);
        assertEquals("Mobile", payment.getPaymentMethod());
    }

    @Test
    public void test_8() {
        mobilePaymentStrategy.setMobileNumber("9876543210");
        assertEquals("9876543210", mobilePaymentStrategy.getMobileNumber());
    }

    @Test
    public void test_9() {
        mobilePaymentStrategy.setProvider("AT&T");
        assertEquals("AT&T", mobilePaymentStrategy.getProvider());
    }

    @Test
    public void test_10() {
        Payment payment = mobilePaymentStrategy.processPayment(50.0);
        assertNotNull(payment);
        assertEquals(50.0, payment.getTotal(), 0.01);
        assertFalse(payment.getIsRefunded());
    }
}
