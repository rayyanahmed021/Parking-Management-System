public class Payment {
    private double total;
    private boolean isRefunded;
    private PaymentStrategy strategy;

    // Constructor
    public Payment(double total, boolean isRefunded, PaymentStrategy strategy) {
        this.total = total;
        this.isRefunded = isRefunded;
        this.strategy = strategy;
    }

    // Getters
    public double getTotal() {
        return total;
    }

    public boolean getIsRefunded() {
        return isRefunded;
    }

    public PaymentStrategy getPaymentMethod() {
        return strategy;
    }

    // Setters
    public void setTotal(double total) {
        this.total = total;
    }

    public void setIsRefunded(boolean isRefunded) {
        this.isRefunded = isRefunded;
    }

    public void setPaymentMethod(PaymentStrategy strategy) {
        this.strategy = strategy;
    }
}
