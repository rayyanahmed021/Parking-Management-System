
package backend;

import java.util.Random;
import java.util.UUID;

public class ParkingLot {
    private String id;
    private String name;
    private ParkingSpace[] parkingSpaces = new ParkingSpace[6];
    private ParkingLotState state;

//    public ParkingLot(String id, String name, ParkingLotState state) {
    public ParkingLot(String id, String name,ParkingLotState state, ParkingSpace[] parkingSpaces) {
        this.id = id;
        this.name = name;
        this.parkingSpaces = parkingSpaces;
        this.state = state;
    }
    
    public static String randomIdGenerator() {
    	//generate id
    	Database db = Database.getInstance();
    	String generatedId;
        boolean idExists;

        do {
            // Generate a random unique identifier
            generatedId = UUID.randomUUID().toString().substring(0, 5);
            idExists = false;
            
            // Check if the generated ID already exists in the database
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
  /// check class diagram if we need this method in diagram
    public void handleStateChange() {
        state.handle(this);
    }
}