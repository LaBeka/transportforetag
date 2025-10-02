package com.myProject.transportCompany.model.vehicle;


public class Truck extends Vehicle {

    public Truck(String regNumber, double capacity) {
        super(regNumber, capacity);
    }

    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String getType() {
        return "Truck";
    }

    @Override
    public boolean hasDriver() {
        return driver != null;
    }
}
