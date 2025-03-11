package backend;

import java.util.ArrayList;

public class AddParkingLotCommand implements ParkingCommand {
    private ParkingLot lot;

    public AddParkingLotCommand(String lotId, String name, String location) {
    	ParkingSpace[] parkingSpace = new ParkingSpace[100];
        this.lot = new ParkingLot(lotId, name, new EnabledState(), parkingSpace, location); //edit this
    	
        for(int i=0; i < 100; i++) {
		parkingSpace[i] = new ParkingSpace(i,this.lot,true);
	}
        this.lot.setParkingSpaces(parkingSpace);
    }

    @Override
    public void execute() {
        Database.getInstance().getAllParkingLots().add(lot);
        for(ParkingSpace parkingSpace: this.lot.getParkingSpaces()) {
        	Database.getInstance().getAllParkingSpaces().add(parkingSpace);
        }
    }
}
