package com.myProject.transportCompany.builderObject;

import com.myProject.transportCompany.interfaces.Assignable;
import com.myProject.transportCompany.model.Driver;
import com.myProject.transportCompany.model.Order;

import java.util.Objects;


public abstract class Vehicle implements Assignable {

    private final String regNumber;
    private Driver driver;
    private boolean isAvailable;

    public Vehicle(Builder builder) {
        this.regNumber = builder.regNumber;
        this.isAvailable = builder.isAvailable;
    }

    public abstract String getType();
    @Override
    public void assignDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public void assignVehicleToOrder(Order order) {
        if(!this.hasDriver()){
            this.isAvailable = true;
            System.out.println("The vehicle has no driver, assign it first!");
        }
        System.out.println("Vehicle assigned to the delivery: " + this.toString() + order.getRoute().toString());
        this.isAvailable = false;
    }

    public String getRegNumber() { return regNumber; }

    public Driver getDriver() { return driver; }

    public boolean isAvailable() { return isAvailable; }

    public void setAvailable(boolean available){  this.isAvailable = available; };

    public boolean hasDriver() { return this.driver != null; }

    @Override
    public String toString() {
        return "%s with reg number: %s %s for route".formatted(getType(), regNumber, isAvailable? "is available" : "IS NOT available");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(regNumber, vehicle.regNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(regNumber);
    }

    public static abstract class Builder<T extends Vehicle, B extends Builder<T, B>> {
        private String regNumber;
        protected boolean isAvailable;

        public B regNumber(String regNumber) {
            this.regNumber = regNumber;
            return self();
        }

        protected abstract B self();       // return correct subclass builder
        public abstract T build();
    }

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(getType());
//        sb.append(" ").append(regNumber).append(" ").append("capacity: " + capacity).append(" ")
//                .append(hasDriver() ? "has assigned driver: " + driver.getName() : "has no assigned driver").append(" ")
//                .append(isAvailable ? "are available for delivery service" : "are unavailable").append(". ");
//        return sb.toString();
//    }
}
