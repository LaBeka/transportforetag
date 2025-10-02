package carSystem.domain;

import carSystem.domain.engines.Engine;
import carSystem.logic.ServiceHistory;

import java.util.List;

public class Car {
    private String model;
    private String registrationNumber;
    private Engine engine;

    private Driver assignedDriver;
    private List<ServiceHistory> serviceLog;
    private double totalFuelConsumed;

    private Car(Builder builder) {
        this.model = builder.model;
        this.registrationNumber = builder.registrationNumber;
        this.engine = builder.engine;
    }

    public static class Builder {
        private String model;
        private String registrationNumber;
        private Engine engine;

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder registrationNumber(String reg) {
            this.registrationNumber = reg;
            return this;
        }

        public Builder engine(Engine engine) {
            this.engine = engine;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }

    public Engine getEngine() {
        return engine;
    }


    public void setAssignedDriver(Driver assignedDriver) {
        this.assignedDriver = assignedDriver;
    }

    public double getTotalFuelConsumed() {
        return totalFuelConsumed;
    }

    public void setTotalFuelConsumed(double currentFuelConsumed) {
        this.totalFuelConsumed += currentFuelConsumed;
    }

    @Override
    public String toString() {
        return "Car " + model + " (" + registrationNumber + "), " + engine;
    }

    public String extendedString() {
        return "Car " +
                model + " (" + registrationNumber + "), "
                + engine + " driver: " + assignedDriver;
    }
}
