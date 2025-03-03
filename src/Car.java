public class Car {
	private String licensePlate;
    private String brand;
    private String color;
    private String type;

    public Car(String licensePlate, String brand, String color, String type) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.color = color;
        this.type = type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
