package carSystem.logic;

import carSystem.domain.Car;
import carSystem.domain.engines.Engine;

public class StopEngine implements Command {
    private Engine engine;

    public StopEngine(Car car) {
        this.engine = car.getEngine();
        car.setTotalFuelConsumed(1.30);
    }

    @Override
    public void execute() {
        engine.stop();
    }
}
