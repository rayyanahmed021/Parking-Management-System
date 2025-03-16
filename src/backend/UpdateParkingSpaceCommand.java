package backend;
public class UpdateParkingSpaceCommand implements ParkingCommand {
    private String actionType;
    private ParkingSpace space;
    private ParkingLot lot;
    
    public UpdateParkingSpaceCommand(String actionType, int spaceId, String lotId) {
        this.actionType = actionType;
        Database db = Database.getInstance();
        for(ParkingLot lot: db.getAllParkingLots()) {
        	if(lot.getId().equals(lotId)) {
        		this.lot = lot;
        		this.space = lot.getParkingSpaces()[spaceId];
        	}
        }
    }

    @Override
    public void execute() {
        if (actionType.equalsIgnoreCase("enable")) {
            space.setEnabled(true);
        } else if (actionType.equalsIgnoreCase("disable")) {
        	space.setEnabled(false);
        }
    }
}
