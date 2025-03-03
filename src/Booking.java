import java.time.LocalDateTime;

public class Booking {
	static int nextBookingId = 0;
    private int id;
    private Client client;
    private double totalPrice;
    private String licensePlate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ParkingSpace parkingSpace;
    private ParkingLot parkingLot;
    private Payment payment;
    
    // Constructor
    public Booking(int id, Client client, double totalPrice, String licensePlate, 
                   LocalDateTime startTime, LocalDateTime endTime, 
                   Payment payment, ParkingSpace parkingSpace, ParkingLot parkingLot) {
        this.id = id;
        this.client = client;
        this.totalPrice = totalPrice;
        this.licensePlate = licensePlate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.payment = payment;
        this.parkingSpace = parkingSpace;
        this.parkingLot = parkingLot;
    }
    public Booking() {
    	
    }

    // Getters
    public int getID() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Payment getPayment() {
        return payment;
    }

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }
    
    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    // Setters
    public void setID(int id) {
        this.id = id;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }
    
    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }
}
