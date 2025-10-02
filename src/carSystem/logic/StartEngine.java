package carSystem.logic;

import carSystem.domain.Car;
import carSystem.domain.engines.Engine;

public class StartEngine implements Command {
    private Engine engine;

    public StartEngine(Car car) {
        this.engine = car.getEngine();
        car.setTotalFuelConsumed(2.99);
    }

    @Override
    public void execute() {
        engine.start();
    }
}
