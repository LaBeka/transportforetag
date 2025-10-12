package com.myProject.transportCompany.generatemodels;

import com.myProject.transportCompany.builderObject.Truck;
import com.myProject.transportCompany.builderObject.Van;
import com.myProject.transportCompany.interfaces.IVehicleCreator;
import com.myProject.transportCompany.builderObject.Vehicle;
import com.myProject.transportCompany.model.Driver;
import com.myProject.transportCompany.model.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class VehicleManagerBuilder implements IVehicleCreator {

    List<Vehicle> vehicles = new ArrayList<>();
    private static volatile VehicleManagerBuilder vehicleInstance;
    private List<Driver> drivers;

    private VehicleManagerBuilder() {}

    public static VehicleManagerBuilder getInstance() {
        if (vehicleInstance == null) {
            synchronized (VehicleManagerBuilder.class) {
                if (vehicleInstance == null) {
                    vehicleInstance = new VehicleManagerBuilder();
                }
            }
        }
        return vehicleInstance;
    }

    @Override
    public Optional<Vehicle> assignVanToOrder(Order order) {
        return vehicles.stream()
                .filter(vehicle -> vehicle.isAvailable() && vehicle.getType().equalsIgnoreCase("van"))
                .findFirst();
    }

    @Override
    public Optional<Vehicle> assignTruckToOrder(Order order) {
        return vehicles.stream()
                .filter(v -> v.isAvailable() && v.getType().equalsIgnoreCase("truck"))
                .findFirst();
    }

    @Override
    public List<Vehicle> getVehicles() {
        return Collections.unmodifiableList(this.vehicles);
    }

    @Override
    public void printNoDriverVehicles() {
        vehicles.stream()
                .filter(v -> !v.hasDriver())
                .forEach(vehicle -> System.out.println(vehicle.toString()));

//        for (Vehicle v : vehicles) {
//            if(!v.hasDriver()) {
//                System.out.println(v.toString());
//            }
//        }
        System.out.println();
    }

    @Override
    public void printAvailableVehicles() {
        System.out.println("Available Vehicles: ");
        vehicles.stream()
                .filter(Vehicle::isAvailable)
                .forEach(vehicle -> System.out.println(vehicle.toString()));
        System.out.println();
    }

    @Override
    public void print() {
        System.out.println("Vehicle: ");
        vehicles.forEach(vehicle -> System.out.println(vehicle.toString()));
        System.out.println();
    }

    @Override
    public void createVehicle(Vehicle vehicle, Optional<Driver> driver) {
        this.vehicles.add(vehicle);
        if(driver.isPresent()){
            vehicle.assignDriver(driver.get());
            driver.get().setAssignedToVehicle(vehicle);
        }
    }

    @Override
    public void initializeVehicles(List<Driver> drivers) {
        this.drivers = drivers;
        generateVehicles();
    }

    @Override
    public void generateVehicles() {

        int regSuffix = 800;

        for (Driver driver : drivers) {
            if (driver.getLicenseClass().value.equalsIgnoreCase("B")) {
                createVehicle(
                        new Truck.Builder()
                                .regNumber("XYZ-" + regSuffix++)
                                .capacity(580.0)
                                .isAvailable(true)
                                .build(),
                        Optional.of(driver));
            } else if (driver.getLicenseClass().value.equalsIgnoreCase("C")) {
                createVehicle(
                        new Van.Builder()
                                .regNumber("XYZ-" + regSuffix++)
                                .isAvailable(true)
                                .build(), Optional.of(driver));
            }
        }

        // Add some vehicles with no drivers (optional)
        createVehicle(new Van.Builder()
                .regNumber("XYZ-" + regSuffix++)
                .build(),
                Optional.empty());
        createVehicle(new Truck.Builder()
                .regNumber("XYZ-" + regSuffix++)
                .capacity(90.0)
                .isAvailable(true)
                .build(),
                Optional.empty());
    }

    @Override
    public Vehicle getOneVehicle(String name) {
        for (Vehicle v : vehicles) {
            if (name.equalsIgnoreCase(v.getType())) {
                return v;
            }
        }
        return null;
    }

    @Override
    public void updateVehicle(Order order) {
        vehicles.stream()
                .filter(vehicle -> vehicle.equals(order.getVehicle()))
                .findFirst()
                .ifPresent(vehicle -> {vehicle.setAvailable(true);});
    }
}
