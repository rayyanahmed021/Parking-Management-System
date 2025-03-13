package backend;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.Duration;

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
        this.id = nextBookingId++;
        this.client = client;
        this.totalPrice = totalPrice;
        this.licensePlate = licensePlate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.payment = payment;
        this.parkingSpace = parkingSpace;
        this.parkingLot = parkingLot;
    }
    @Override
    public String toString() {
        return "Booking ID: " + this.id + " | License Plate: " + this.licensePlate + 
               " | Start: " + this.startTime + " | End: " + this.endTime;
    }

    public Booking() {
    	this.id = nextBookingId++;
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
        if (id >= nextBookingId) {
            nextBookingId = id + 1;
        }
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setTotalPrice(double totalPrice) {
    	// this.payment.setTotal(totalPrice);
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
    
    public double checkRefund() {
    	Duration duration = Duration.between(this.startTime, LocalDateTime.now());
    	long hours = duration.toHours();
    	if (hours >= 1) {
    		return - 1;
    	}
    	else {
    		return this.totalPrice;
    	}
    }
    
    public double calculateCheckout() {
    	Duration d = Duration.between(this.startTime, this.endTime);
    	long hrs = d.toHours();
    	return (hrs - 1) * this.client.calculateDepositClient();
    }
    
}
