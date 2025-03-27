package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import backend.*;

public class PaymentTest {
    
    private Payment payment;
    private CreditCardStrategy creditCardStrategy;
    private DebitCardStrategy debitCardStrategy;
    private MobilePaymentStrategy mobilePaymentStrategy;
    private PayPalStrategy payPalStrategy;

    @Before
    public void setUp() throws Exception {
        creditCardStrategy = new CreditCardStrategy(1234567890123456L, "John Doe", "123", "12/23");
        debitCardStrategy = new DebitCardStrategy(9876543210987654L, "Alice Smith", "321", "11/24");
        mobilePaymentStrategy = new MobilePaymentStrategy("1234567890", "Verizon");
        payPalStrategy = new PayPalStrategy("johndoe@example.com", "password123");
        
        payment = new Payment(100.0, false, creditCardStrategy);
    }

    @Test
    public void test_1() {
        assertEquals(100.0, payment.getTotal(), 0.01);
    }

    @Test
    public void test_2() {
        assertFalse(payment.getIsRefunded());
    }

    @Test
    public void test_3() {
        assertEquals("Credit Card", payment.getPaymentMethod());
    }

    @Test
    public void test_4() {
        Payment processedPayment = payment.payAmount(100.0);
        assertNotNull(processedPayment);
        assertEquals(100.0, processedPayment.getTotal(), 0.01);
        assertFalse(processedPayment.getIsRefunded());
    }

    @Test
    public void test_5() {
        payment.setPaymentMethod(debitCardStrategy);
        Payment processedPayment = payment.payAmount(200.0);
        assertNotNull(processedPayment);
        assertEquals(200.0, processedPayment.getTotal(), 0.01);
        assertFalse(processedPayment.getIsRefunded());
    }

    @Test
    public void test_6() {
        payment.setPaymentMethod(mobilePaymentStrategy);
        Payment processedPayment = payment.payAmount(50.0);
        assertNotNull(processedPayment);
        assertEquals(50.0, processedPayment.getTotal(), 0.01);
        assertFalse(processedPayment.getIsRefunded());
    }

    @Test
    public void test_7() {
        payment.setPaymentMethod(payPalStrategy);
        Payment processedPayment = payment.payAmount(150.0);
        assertNotNull(processedPayment);
        assertEquals(150.0, processedPayment.getTotal(), 0.01);
        assertFalse(processedPayment.getIsRefunded());
    }

    @Test
    public void test_8() {
        payment.setIsRefunded(true);
        assertTrue(payment.getIsRefunded());
    }

    @Test
    public void test_9() {
        Payment payment1 = new Payment(50.0, false, creditCardStrategy);
        Payment payment2 = new Payment(75.0, false, debitCardStrategy);
        assertNotEquals(payment1.getId(), payment2.getId());
    }

    @Test
    public void test_10() {
        payment.setTotal(200.0);
        assertEquals(200.0, payment.getTotal(), 0.01);
        
        payment.setPaymentMethod(mobilePaymentStrategy);
        Payment processedPayment = payment.payAmount(200.0);
        assertNotNull(processedPayment);
        assertEquals(200.0, processedPayment.getTotal(), 0.01);
    }

    @Test
    public void test_11() {
        payment.setIsRefunded(true);
        assertTrue(payment.getIsRefunded());
        payment.setIsRefunded(false);
        assertFalse(payment.getIsRefunded());
    }

    @Test
    public void test_12() {
        Payment newPayment = new Payment();
        assertNotNull(newPayment);
        assertEquals(0.0, newPayment.getTotal(), 0.01);
        assertFalse(newPayment.getIsRefunded());
    }

    @Test
    public void test_13() {
        payment.setPaymentMethod(creditCardStrategy);
        assertEquals("Credit Card", payment.getPaymentMethod());
        
        payment.setPaymentMethod(debitCardStrategy);
        assertEquals("Debit Card", payment.getPaymentMethod());
        
        payment.setPaymentMethod(mobilePaymentStrategy);
        assertEquals("Mobile", payment.getPaymentMethod());
        
        payment.setPaymentMethod(payPalStrategy);
        assertEquals("PayPal", payment.getPaymentMethod());
    }

    @Test
    public void test_14() {
        assertTrue(payment.getPaymentStrategy() instanceof CreditCardStrategy);
        
        payment.setPaymentMethod(debitCardStrategy);
        assertTrue(payment.getPaymentStrategy() instanceof DebitCardStrategy);
        
        payment.setPaymentMethod(mobilePaymentStrategy);
        assertTrue(payment.getPaymentStrategy() instanceof MobilePaymentStrategy);
        
        payment.setPaymentMethod(payPalStrategy);
        assertTrue(payment.getPaymentStrategy() instanceof PayPalStrategy);
    }
}
