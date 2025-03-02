public class ParkingSpace {
    private int id;
    private ParkingLot parkingLot;
    private boolean isOccupied;
    private String location;

    public ParkingSpace(int id, ParkingLot parkingLot, boolean isOccupied, String location) {
        this.id = id;
        this.parkingLot = parkingLot;
        this.isOccupied = isOccupied;
        this.location = location;
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
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}