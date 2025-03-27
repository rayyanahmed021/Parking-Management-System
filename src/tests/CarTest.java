package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import backend.Car;

public class CarTest {
    private Car car;

    @Before
    public void test1() {
        car = new Car("ABC123", "Toyota", "Red", "Sedan");
    }

    @Test
    public void test2() {
        assertEquals("ABC123", car.getLicensePlate());
    }

    @Test
    public void test3() {
        car.setLicensePlate("XYZ789");
        assertEquals("XYZ789", car.getLicensePlate());
    }

    @Test
    public void test4() {
        assertEquals("Toyota", car.getBrand());
    }

    @Test
    public void test5() {
        car.setBrand("Honda");
        assertEquals("Honda", car.getBrand());
    }

    @Test
    public void test6() {
        assertEquals("Red", car.getColor());
    }

    @Test
    public void test7() {
        car.setColor("Blue");
        assertEquals("Blue", car.getColor());
    }

    @Test
    public void test8() {
        assertEquals("Sedan", car.getType());
    }

    @Test
    public void test9() {
        car.setType("SUV");
        assertEquals("SUV", car.getType());
    }

    @Test
    public void test10() {
        Car newCar = new Car("LMN456", "Ford", "Black", "Truck");
        assertEquals("LMN456", newCar.getLicensePlate());
        assertEquals("Ford", newCar.getBrand());
        assertEquals("Black", newCar.getColor());
        assertEquals("Truck", newCar.getType());
    }
}
