package backend;
public class CreditCardStrategy implements PaymentStrategy {
    private long cardNumber;
    private String cardHolderName;
    private String cvv;
    private String expiryDate;

    public CreditCardStrategy(long cardNumber, String cardHolderName, String cvv, String expiryDate) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    @Override
    public Payment processPayment(double amount) {
        System.out.println("Processing credit card payment of $" + amount);
        return new Payment(++Payment.nextPaymentId,amount, false, this);
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCVV() {
        return cvv;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public void setCVV(String cvv) {
        this.cvv = cvv;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
