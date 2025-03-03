public class UpdateParkingLotCommand implements ParkingCommand {
    private String actionType;
    private ParkingLot lot;

    public UpdateParkingLotCommand(String actionType, ParkingLot lot) {
        this.actionType = actionType;
        this.lot = lot;
    }

    @Override
    public void execute() {
        if (actionType.equalsIgnoreCase("enable")) {
            lot.setState(new EnabledState());
            //System.out.println("enabled.");
        } else if (actionType.equalsIgnoreCase("disable")) {
            lot.setState(new DisabledState());
           // System.out.println("disabled.");
        }
    }
}
