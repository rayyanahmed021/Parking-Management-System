public class ParkingLot {
    private String id;
    private String name;
    private ParkingSpace[] parkingSpaces = new ParkingSpace[6];
//    private ParkingLotState state;

//    public ParkingLot(String id, String name, ParkingLotState state) {
    public ParkingLot(String id, String name) {
        this.id = id;
        this.name = name;
//        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParkingSpace[] getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(ParkingSpace[] parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

//    public ParkingLotState getState() {
//        return state;
//    }

//    public void setState(ParkingLotState state) {
//        this.state = state;
//    }
}