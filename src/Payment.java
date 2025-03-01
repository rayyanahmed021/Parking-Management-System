public class Payment {
	static int nextPaymentId;
	private int id;
    private double total;
    private boolean isRefunded;
    private PaymentStrategy strategy;


    // Constructors
    public Payment() {
    	
    }
    // Constructor
    public Payment(int id, double total, boolean isRefunded, PaymentStrategy strategy) {
        this.id = nextPaymentId++;
        this.total = total;
        this.isRefunded = isRefunded;
        this.strategy = strategy;
    }

    // Getters
    public double getId() {
        return id;
    }
    
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
    
    public Payment payAmount(double amount) {
    	return this.strategy.processPayment(amount);
    }
}
