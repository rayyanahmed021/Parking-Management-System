package backend;

public interface ParkingLotState {
	 void handle(ParkingLot lot);
	 boolean isEnabled();
}
