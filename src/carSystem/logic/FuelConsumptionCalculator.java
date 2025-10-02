package carSystem.logic;

public class FuelConsumptionCalculator implements FuelConsumption {
    @Override
    public double calculate(double distance, double fuelUsed) {
        return distance / fuelUsed;
    }
}
