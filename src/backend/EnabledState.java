package backend;
class EnabledState implements ParkingLotState {
    @Override
    public void handle(ParkingLot lot) {
        System.out.println("Parking Lot " + lot.getName() + " is ENABLED.");
    }
}