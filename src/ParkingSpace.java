public class ParkingSpace {
    private int id;
    private ParkingLot parkingLot;
    private boolean isOccupied;
    private String location;
    private ParkingSensor parkingSensor;

    public ParkingSpace(int id, ParkingLot parkingLot, String location) {
        this.id = id;
        this.parkingLot = parkingLot;
        this.isOccupied = false;
        this.location = location;
        this.parkingSensor = new ParkingSensor(this);
    }
    
    public ParkingSpace() {
    	
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
        parkingSensor.notifyObservers();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public ParkingSensor getParkingSensor() {
        return parkingSensor;
    }

    public void setParkingSensor(ParkingSensor parkingSensor) {
        this.parkingSensor = parkingSensor;
    }
}