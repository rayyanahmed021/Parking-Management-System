public class UpdateParkingSpaceCommand implements ParkingCommand {
    private String actionType;
    private ParkingSpace space;

    public UpdateParkingSpaceCommand(String actionType, ParkingSpace space) {
        this.actionType = actionType;
        this.space = space;
    }

    @Override
    public void execute() {
        if (actionType.equalsIgnoreCase("enable")) {
            space.setOccupied(false);
           // System.out.println("enabled.");
        } else if (actionType.equalsIgnoreCase("disable")) {
            space.setOccupied(true);
            //System.out.println("disabled.");
        }
    }
}
