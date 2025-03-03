public class AddParkingLotCommand implements ParkingCommand {
    private String lotId;
    private String location;
    private ParkingLot lot;

    public AddParkingLotCommand(String lotId, String location, ParkingLot lot) {
        this.lotId = lotId;
        this.location = location;
        this.lot = lot;
    }

    @Override
    public void execute() {
        Database.getInstance().getAllParkingLots().add(lot);
       // System.out.println("Parking lot " + lotId + " added at " + location);
    }
}
