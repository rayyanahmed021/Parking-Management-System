package backend;
import java.util.ArrayList;
import java.util.List;

public class ParkingSensor {
    private ParkingSpace parkingSpace;
    private Car carInfo;
    private List<ParkingObserver> observers;

    public ParkingSensor(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
        this.observers = new ArrayList<>();
    }

    public boolean checkSpaceAvailable() {
        return !parkingSpace.isOccupied();
    }

    public void setCarInfo(Car car) {
        this.carInfo = car;
    }

    public Car getCarInfo() {
        return carInfo;
    }

    public void addObserver(ParkingObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ParkingObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (ParkingObserver observer : observers) {
            observer.update(parkingSpace.getId(), parkingSpace.isOccupied());
        }
    }
}
