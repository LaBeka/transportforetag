package carSystem.initalization;

import carSystem.domain.Car;
import carSystem.domain.Driver;
import carSystem.domain.engines.Engine;
import carSystem.domain.types.EngineType;
import carSystem.domain.types.LicenceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Populate {
    private List<Engine> engineList;
    private List<Car> carList;
    private List<Driver> drivers;

    public Populate() {
        populate();
    }

    public void populate(){
        this.engineList = new ArrayList<>(Arrays.asList(
                new Engine.Builder()
                        .type("V4").horsepower(199).fuelType(EngineType.DIESEL).build(),
                new Engine.Builder()
                        .type("V5").horsepower(99).fuelType(EngineType.PETROLEUM).build(),
                new Engine.Builder()
                        .type("V6").horsepower(250).fuelType(EngineType.PETROLEUM).build(),
                new Engine.Builder()
                        .type("V7").horsepower(150).fuelType(EngineType.DIESEL).build(),
                new Engine.Builder()
                        .type("V8").horsepower(155).fuelType(EngineType.ELECTRIC).build(),
                new Engine.Builder()
                        .type("V9").horsepower(255).fuelType(EngineType.ELECTRIC).build()
        ));

        this.carList = new ArrayList<>(Arrays.asList(
                RegisterNewCar.createCar("Volvo XC60", "XYZ-887", engineList.get(0)),
                RegisterNewCar.createCar("Toyota XC60", "XYZ-888", engineList.get(1)),
                RegisterNewCar.createCar("Mazda XC60", "XYZ-889", engineList.get(2)),
                RegisterNewCar.createCar("KIA XC60", "XYZ-890", engineList.get(3)),
                RegisterNewCar.createCar("Tesla XC60", "XYZ-891", engineList.get(4)),
                RegisterNewCar.createCar("Tesla XXX", "XYZ-892", engineList.get(5))
        ));

        this.drivers = new ArrayList<>(Arrays.asList(
                new Driver.Builder()
                        .name("Anna Smith").licenceType(LicenceType.B).experience(1).build(),
                new Driver.Builder()
                        .name("Joe Doe").licenceType(LicenceType.B).experience(2).build(),
                new Driver.Builder()
                        .name("Hanna Doe").licenceType(LicenceType.B).experience(3).build(),
                new Driver.Builder()
                        .name("Joe Smith").licenceType(LicenceType.B).experience(5).build(),
                new Driver.Builder()
                        .name("Ray Sm").licenceType(LicenceType.B).experience(10).build(),
                new Driver.Builder()
                        .name("Sam Kal").licenceType(LicenceType.B).experience(15).build()
        ));
    }

    public List<Engine> getEngineList() {
        return engineList;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }
}
