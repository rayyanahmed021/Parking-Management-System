package backend;

import java.util.ArrayList;

public class AddParkingLotCommand implements ParkingCommand {
    private ParkingLot lot;

    public AddParkingLotCommand(String lotId, String name) {
    	ParkingSpace[] spaces = new ParkingSpace[6];
    	Database db = Database.getInstance();
    	ArrayList<ParkingLot> lots = db.getAllParkingLots();
    	ParkingLot lot = null;
    	
    	for (ParkingLot l: lots) {
    		if (lotId == l.getId()) {
    			lot = l;
    			break;
    		}
    	}
    	for (int i = 0; i < spaces.length; i++) {
    		spaces[i] = new ParkingSpace(i, lot, "", true);
    	}
        this.lot = new ParkingLot(lotId, name, spaces); //edit this
    }

    @Override
    public void execute() {
        Database.getInstance().getAllParkingLots().add(lot);
       // System.out.println("Parking lot " + lotId + " added at " + location);
    }
}
