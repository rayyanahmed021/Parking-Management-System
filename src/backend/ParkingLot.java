package backend;

import java.util.Random;
import java.util.UUID;

public class ParkingLot {
    private String id;
    private String name;
    private ParkingSpace[] parkingSpaces = new ParkingSpace[100];
    private ParkingLotState state;
    private String location;

    public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
    public ParkingLot(String id, String name,ParkingLotState state, ParkingSpace[] parkingSpaces, String location) {
        this.id = id;
        this.name = name;
        this.parkingSpaces = parkingSpaces;
        this.state = state;
        this.location = location;
    }
    
    public static String randomIdGenerator() {
    	Database db = Database.getInstance();
    	String generatedId;
        boolean idExists;

        do {
            generatedId = UUID.randomUUID().toString().substring(0, 5);
            idExists = false;
            
            for (ParkingLot lot : db.getAllParkingLots()) {
                if (lot.getId().equals(generatedId)) {
                    idExists = true;
                    break;
                }
            }
        } while (idExists);
        
        return generatedId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParkingSpace[] getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(ParkingSpace[] parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    public void setState(ParkingLotState state) {
        this.state = state;
    }

    public ParkingLotState getState() {
        return state;
    }

    public void handleStateChange() {
        state.handle(this);
    }
}