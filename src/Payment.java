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
        this.id = id;
        this.total = total;
        this.isRefunded = isRefunded;
        this.strategy = strategy;
    }

    // Getters
    public int getId() {
        return id;
    }
    
    public double getTotal() {
        return total;
    }

    public boolean getIsRefunded() {
        return isRefunded;
    }

    public PaymentStrategy getPaymentStrategy() {
        return strategy;
    }
    
    public String getPaymentMethod() {
    	String strategy = "";
    	
    	if (this.strategy instanceof CreditCardStrategy) {
    		strategy = "Credit Card";
    	}
    	else if (this.strategy instanceof DebitCardStrategy) {
    		strategy = "Debit Card";
    	}
    	else if (this.strategy instanceof PayPalStrategy) {
    		strategy = "PayPal";
    	}
    	else if (this.strategy instanceof MobilePaymentStrategy) {
    		strategy = "Mobile";
    	}
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
