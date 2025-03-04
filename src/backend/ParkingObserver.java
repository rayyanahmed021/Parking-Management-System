package backend;
public interface ParkingObserver {
    void update(int spaceID, String lotId, boolean isOccupied);
}
