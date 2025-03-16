package backend;
public class MobilePaymentStrategy implements PaymentStrategy {
    private String mobileNumber;
    private String provider;

    public MobilePaymentStrategy(String mobileNumber, String provider) {
        this.mobileNumber = mobileNumber;
        this.provider = provider;
    }

    @Override
    public Payment processPayment(double amount) {
        System.out.println("Processing mobile payment of $" + amount);
        return new Payment(++Payment.nextPaymentId, amount, false, this);
    }
    
    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getProvider() {
        return provider;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
