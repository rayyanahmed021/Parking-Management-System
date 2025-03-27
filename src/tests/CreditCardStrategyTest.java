package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import backend.*;

public class CreditCardStrategyTest {
    
    private CreditCardStrategy creditCardStrategy;

    @Before
    public void setUp() throws Exception {
        creditCardStrategy = new CreditCardStrategy(1234567890123456L, "John Doe", "123", "12/23");
    }

    @Test
    public void test_1() {
        assertEquals(1234567890123456L, creditCardStrategy.getCardNumber());
    }

    @Test
    public void test_2() {
        assertEquals("John Doe", creditCardStrategy.getCardHolderName());
    }

    @Test
    public void test_3() {
        assertEquals("123", creditCardStrategy.getCVV());
    }

    @Test
    public void test_4() {
        assertEquals("12/23", creditCardStrategy.getExpiryDate());
    }

    @Test
    public void test_5() {
        Payment payment = creditCardStrategy.processPayment(100.0);
        assertNotNull(payment);
        assertEquals(100.0, payment.getTotal(), 0.01);
        assertFalse(payment.getIsRefunded());
    }

    @Test
    public void test_6() {
        creditCardStrategy.setCardNumber(6543219876543210L);
        assertEquals(6543219876543210L, creditCardStrategy.getCardNumber());
    }

    @Test
    public void test_7() {
        creditCardStrategy.setCardHolderName("Jane Smith");
        assertEquals("Jane Smith", creditCardStrategy.getCardHolderName());
    }

    @Test
    public void test_8() {
        creditCardStrategy.setCVV("456");
        assertEquals("456", creditCardStrategy.getCVV());
    }

    @Test
    public void test_9() {
        creditCardStrategy.setExpiryDate("11/25");
        assertEquals("11/25", creditCardStrategy.getExpiryDate());
    }

    @Test
    public void test_10() {
        Payment payment1 = creditCardStrategy.processPayment(150.0);
        assertEquals(150.0, payment1.getTotal(), 0.01);
        
        Payment payment2 = creditCardStrategy.processPayment(250.0);
        assertEquals(250.0, payment2.getTotal(), 0.01);
    }

    @Test
    public void test_11() {
        Payment payment = new Payment(100.0, false, creditCardStrategy);
        assertEquals("Credit Card", payment.getPaymentMethod());
    }
}
