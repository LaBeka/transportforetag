package com.myProject.transportCompany.model;

import com.myProject.transportCompany.model.vehicle.Vehicle;

public class Driver {
    private String name;
    private LicenceType licenseClass;
    private String schedule;
    private Vehicle assignVehicle;

    public Driver(String name, LicenceType licenseClass, String schedule) {
        this.name = name;
        this.licenseClass = licenseClass;
        this.schedule = schedule;
    }

    public LicenceType getLicenseClass() {
        return licenseClass;
    }

    public boolean isAssignedToVehicle() {
        return assignVehicle != null;
    }

    public void setAssignedToVehicle(Vehicle vehicle) {
        this.assignVehicle = vehicle;
    }

    public String getName() { return name; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        sb.append(", ");
        sb.append(this.schedule);
        sb.append(", licenseClass = ");
        sb.append(this.licenseClass);
        return sb.toString();
    }
}
