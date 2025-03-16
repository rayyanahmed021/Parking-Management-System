package backend;

public class PayPalStrategy implements PaymentStrategy {
    private String username;
    private String password;

    public PayPalStrategy(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Payment processPayment(double amount) {
        System.out.println("Processing PayPal payment of $" + amount);
        return new Payment(++Payment.nextPaymentId, amount, false, this);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
