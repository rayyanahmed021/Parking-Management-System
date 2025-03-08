package backend;
class DisabledState implements ParkingLotState {
    @Override
    public void handle(ParkingLot lot) {
        System.out.println("Parking Lot " + lot.getName() + " is DISABLED.");
    }

	@Override
	public boolean isEnabled() {
		return false;
	}
}