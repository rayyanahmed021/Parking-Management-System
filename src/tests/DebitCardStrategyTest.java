package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import backend.*;

public class DebitCardStrategyTest {

    private DebitCardStrategy debitCardStrategy;

    @Before
    public void setUp() throws Exception {
        debitCardStrategy = new DebitCardStrategy(9876543210987654L, "Alice Smith", "321", "11/24");
    }

    @Test
    public void test_1() {
        assertEquals(9876543210987654L, debitCardStrategy.getCardNumber());
    }

    @Test
    public void test_2() {
        assertEquals("Alice Smith", debitCardStrategy.getCardHolderName());
    }

    @Test
    public void test_3() {
        assertEquals("321", debitCardStrategy.getCVV());
    }

    @Test
    public void test_4() {
        assertEquals("11/24", debitCardStrategy.getExpiryDate());
    }

    @Test
    public void test_5() {
        Payment payment = debitCardStrategy.processPayment(200.0);
        assertNotNull(payment);
        assertEquals(200.0, payment.getTotal(), 0.01);
        assertFalse(payment.getIsRefunded());
    }

    @Test
    public void test_6() {
        debitCardStrategy.setCardNumber(1234567890123456L);
        assertEquals(1234567890123456L, debitCardStrategy.getCardNumber());
    }

    @Test
    public void test_7() {
        debitCardStrategy.setCardHolderName("Bob Johnson");
        assertEquals("Bob Johnson", debitCardStrategy.getCardHolderName());
    }

    @Test
    public void test_8() {
        debitCardStrategy.setCVV("654");
        assertEquals("654", debitCardStrategy.getCVV());
    }

    @Test
    public void test_9() {
        debitCardStrategy.setExpiryDate("10/25");
        assertEquals("10/25", debitCardStrategy.getExpiryDate());
    }

    @Test
    public void test_10() {
        Payment payment1 = debitCardStrategy.processPayment(300.0);
        assertEquals(300.0, payment1.getTotal(), 0.01);
        
        Payment payment2 = debitCardStrategy.processPayment(450.0);
        assertEquals(450.0, payment2.getTotal(), 0.01);
    }

    @Test
    public void test_11() {
        Payment payment = new Payment(200.0, false, debitCardStrategy);
        assertEquals("Debit Card", payment.getPaymentMethod());
    }
}
