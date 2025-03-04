package backend;
public class AddParkingLotCommand implements ParkingCommand {
    private ParkingLot lot;

    public AddParkingLotCommand(String lotId, String name) {
        this.lot = new ParkingLot(lotId, name, new ParkingSpace[6]); //edit this
    }

    @Override
    public void execute() {
        Database.getInstance().getAllParkingLots().add(lot);
       // System.out.println("Parking lot " + lotId + " added at " + location);
    }
}
