
package backend;

public class ParkingSpace implements ParkingObserver{

    private int id;
    private ParkingLot parkingLot;
    private boolean isOccupied;
    private ParkingSensor parkingSensor;
    private boolean isEnabled;

    public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public ParkingSpace(int id, ParkingLot parkingLot, boolean isEnabled) {
        this.id = id;
        this.parkingLot = parkingLot;
        this.isOccupied = false;
        this.parkingSensor = new ParkingSensor(this);
        this.isEnabled = isEnabled;
    }
    
    public ParkingSpace() {
    	this.parkingSensor = new ParkingSensor(this);
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

    public ParkingSensor getParkingSensor() {
        return parkingSensor;
    }

    public void setParkingSensor(ParkingSensor parkingSensor) {
        this.parkingSensor = parkingSensor;
    }

	@Override
	public void update(int spaceID, String lotId, boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
}