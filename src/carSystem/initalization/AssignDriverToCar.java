package carSystem.initalization;

import carSystem.domain.Car;
import carSystem.domain.Driver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignDriverToCar {
    private Map<Car, Driver> assignments = new HashMap<>();


    public void assignDriverToCar(Car car, Driver driver) {
        car.setAssignedDriver(driver);
        assignments.put(car, driver);
    }

    public Driver getDriver(Car car) {
        return assignments.get(car);
        //car.getAssignedDriver();
    }

    public void assignDriversToCars(List<Car> cars, List<Driver> drivers) {
        if(cars.size() == drivers.size()) {
            for(int i = 0; i < cars.size(); i++) {
                assignDriverToCar(cars.get(i), drivers.get(i));
            }
        }

    }
}
