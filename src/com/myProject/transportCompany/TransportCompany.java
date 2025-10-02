package com.myProject.transportCompany;

import com.myProject.transportCompany.generatemodels.CustomerManager;
import com.myProject.transportCompany.generatemodels.DriverManager;
import com.myProject.transportCompany.generatemodels.OrderManager;
import com.myProject.transportCompany.generatemodels.VehicleManager;
import com.myProject.transportCompany.model.*;

import java.util.*;

public class TransportCompany {
    private static volatile TransportCompany singleObject;

    private TransportCompany() {}

    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        Map<String, Runnable> menu = new LinkedHashMap<>();
        menu.put("List drivers", () -> DriverManager.getInstance().printDriverList());
        menu.put("Show available drivers", () -> DriverManager.getInstance().printAvailableDriverList());
        menu.put("List vehicles", () -> VehicleManager.getInstance().print());
        menu.put("Show available vehicles with driver ready for route", () -> VehicleManager.getInstance().printAvailableVehicles());
        menu.put("List vehicles with no driver", () -> VehicleManager.getInstance().printNoDriverVehicles());

        menu.put("Manage customers", () -> {
            Map<String, Runnable> submenu = new LinkedHashMap<>();
            submenu.put("List customers", () -> CustomerManager.getInstance().print());
            submenu.put("Create order", () -> OrderManager.getInstance().initiateOrderManager(
                    CustomerManager.getInstance().getList(),
                    VehicleManager.getInstance().getVehicles()));
            submenu.put("List of customers that need to be served: ", () -> CustomerManager.getInstance().printCustomersInTheQueue());
            submenu.put("List delivery history: ", () -> OrderManager.getInstance().print());

            InputHandler.runMainMenu(scanner, submenu);
        });
        InputHandler.runMainMenu(scanner, menu);
        scanner.close();
    }


    public void run() {
        DriverManager.getInstance().initializeDrivers();
        List<Driver> driverList = DriverManager.getInstance().getDriverList();
        VehicleManager.getInstance().initializeVehicles(driverList);
        CustomerManager.getInstance().getList();
        runMenu();
    }

    public static TransportCompany getInstance(){

        if(singleObject == null){
            synchronized (TransportCompany.class){
                if(singleObject == null){
                    singleObject = new TransportCompany();
                }
            }
        }
        return singleObject;
    }
}
