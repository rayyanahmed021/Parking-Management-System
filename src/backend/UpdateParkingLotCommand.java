package backend;
public class UpdateParkingLotCommand implements ParkingCommand {
    private String actionType;
    private ParkingLot lot;

    public UpdateParkingLotCommand(String actionType, String lotId) {
        this.actionType = actionType;
        Database db = Database.getInstance();
        for(ParkingLot lot: db.getAllParkingLots()) {
        	if(lot.getId().equals(lotId)) {
        		this.lot = lot;
        	}
        }
    }

    @Override
    public void execute() {
        if (actionType.equalsIgnoreCase("enable")) {
            lot.setState(new EnabledState());
        } else if (actionType.equalsIgnoreCase("disable")) {
            lot.setState(new DisabledState());
        }
    }
}
