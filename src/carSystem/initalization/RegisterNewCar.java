package carSystem.initalization;

import carSystem.domain.Car;
import carSystem.domain.engines.Engine;


public class RegisterNewCar {

    public static Car createCar(String model, String regNum, Engine engine) {
        return new Car.Builder()
                .model(model)
                .registrationNumber(regNum)
                .engine(engine)
                .build();
    }

}
