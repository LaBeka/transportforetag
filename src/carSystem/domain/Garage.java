package carSystem.domain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Garage {

    private List<Car> cars;
    private List<Car> carsInService;

    private Map<String, Car> carhash;


    public Garage() {
        this.cars = new LinkedList<>();
        this.carsInService = new LinkedList<Car>();
        this.carhash = new HashMap<>();
    }

    public void addCar(Car car) {
        this.cars.add(car);
    }

    public List<Car> getCars() {
        return cars;
    }

    public Map<String, Car> getCarhash() {
        return carhash;
    }

    public void addCarhash(Car car) {
        this.carhash.put(car.getClass().getName(), car);
    }

    public void removeCar(Car car) {
        this.cars.remove(car);
    }
}
