//package com.myProject.transportCompany.generatemodels;
//
//import com.myProject.transportCompany.interfaces.IVehicleCreator;
//import com.myProject.transportCompany.model.Customer;
//import com.myProject.transportCompany.model.Driver;
//import com.myProject.transportCompany.model.Order;
//import com.myProject.transportCompany.model.vehicle.Truck;
//import com.myProject.transportCompany.model.vehicle.Van;
//import com.myProject.transportCompany.model.vehicle.Vehicle;
//
//import java.util.*;
//
//public class VehicleManager implements IVehicleCreator {
//
//    List<Vehicle> vehicles = new ArrayList<>();
//    private static volatile VehicleManager vehicleInstance;
//    private List<Driver> drivers;
//
//    private VehicleManager() {}
//
//    @Override
//    public Optional<Vehicle> assignVanToOrder(Order order) {
//        Vehicle van = null;
//        for (Vehicle v : vehicles) {
//            if(v.isAvailable() && v.getType().equalsIgnoreCase("van")) {
//                v.assignVehicleToOrder(order);
//                van = v;
//                break;
//            }
//        }
//        return Optional.of(van);
//    }
//
//    @Override
//    public Optional<Vehicle> assignTruckToOrder(Order order) {
//        Vehicle van = null;
//        for (Vehicle v : vehicles) {
//            if(v.isAvailable() && v.getType().equalsIgnoreCase("truck")) {
//                v.assignVehicleToOrder(order);
//                van = v;
//            }
//        }
//        return Optional.of(van);
//    }
//
//    @Override
//    public List<Vehicle> getVehicles() {
//        return Collections.unmodifiableList(this.vehicles);
//    }
//
//    @Override
//    public void printNoDriverVehicles() {
//        for (Vehicle v : vehicles) {
//            if(!v.hasDriver()) {
//                System.out.println(v.toString());
//            }
//        }
//        System.out.println();
//    }
//
//    @Override
//    public void printAvailableVehicles() {
//        for (Vehicle v : vehicles) {
//            if(v.isAvailable()) {
//                System.out.println(v.toString());
//            }
//        }
//        System.out.println();
//    }
//
//    @Override
//    public void print() {
//        System.out.println("Vehicle: ");
//        for (Vehicle v : vehicles) {
//            System.out.println(v.toString());
//        }
//        System.out.println();
//    }
//
//    @Override
//    public void createVehicle(Vehicle vehicle, Optional<Driver> driver) {
//        this.vehicles.add(vehicle);
//        if(driver.isPresent()){
//            vehicle.assignDriver(driver.get());
//            driver.get().setAssignedToVehicle(vehicle);
//        }
//    }
//
//    @Override
//    public void initializeVehicles(List<Driver> drivers) {
//        this.drivers = drivers;
//        generateVehicles();
//    }
//
//    @Override
//    public void generateVehicles(){
//        int regSuffix = 800;
//
//        for (Driver driver : drivers) {
//            if (driver.getLicenseClass().value.equalsIgnoreCase("B")) {
//                createVehicle(new Truck("XYZ-" + regSuffix++, 580.0), Optional.of(driver));
//            } else if (driver.getLicenseClass().value.equalsIgnoreCase("C")) {
//                createVehicle(new Van("XYZ-" + regSuffix++, 1040.0), Optional.of(driver));
//            }
//        }
//
//        // Add some vehicles with no drivers (optional)
//        createVehicle(new Van("XYZ-" + regSuffix++, 500.0),  Optional.empty());
//        createVehicle(new Truck("XYZ-" + regSuffix++, 900.0), Optional.empty());
//
//    }
//
//    public static VehicleManager getInstance() {
//        if (vehicleInstance == null) {
//            synchronized (VehicleManager.class) {
//                if (vehicleInstance == null) {
//                    vehicleInstance = new VehicleManager();
//                }
//            }
//        }
//        return vehicleInstance;
//    }
//
//
//    @Override
//    public Vehicle getOneVehicle(String name) {
//        for (Vehicle v : vehicles) {
//            if (name.equalsIgnoreCase(v.getType())) {
//                return v;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public void updateVehicle(Order order){
//        for (Vehicle v : vehicles) {
//            if (order.getVehicle().equals(v)) {
//                v.setAvailable(false);
//                break;
//            }
//        }
//    }
//}
