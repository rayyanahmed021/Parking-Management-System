package backend;

public interface PaymentStrategy {
    Payment processPayment(double amount);
}
