package com.myProject.transportCompany.model.vehicle;

import com.myProject.transportCompany.interfaces.Assignable;
import com.myProject.transportCompany.model.Driver;
import com.myProject.transportCompany.model.Order;
import com.myProject.transportCompany.model.Route;


public abstract class Vehicle implements Assignable {

    protected String regNumber;
    protected double capacity;
    protected Driver driver;
    protected boolean isAvailable;

    public Vehicle(String regNumber, double capacity) {
        this.regNumber = regNumber;
        this.capacity = capacity;
        this.isAvailable = true;
    }

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

    public abstract boolean isAvailable();

    public abstract void setAvailable(boolean available);

    public abstract String getType();

    public abstract boolean hasDriver();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getType());
        sb.append(" ").append(regNumber).append(" ").append("capacity: " + capacity).append(" ")
                .append(hasDriver() ? "has assigned driver: " + driver.getName() : "has no assigned driver").append(" ")
                .append(isAvailable ? "are available for delivery service" : "are unavailable").append(". ");
        return sb.toString();
    }
}
