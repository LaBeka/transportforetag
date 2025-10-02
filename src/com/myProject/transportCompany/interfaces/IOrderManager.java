package com.myProject.transportCompany.interfaces;

import com.myProject.transportCompany.model.Customer;
import com.myProject.transportCompany.model.Order;
import com.myProject.transportCompany.builderObject.Vehicle;

import java.util.List;
import java.util.Scanner;

public interface IOrderManager {

    void initiateOrderManager(List<Customer> customers, List<Vehicle> vehicles);

    void discussOrder();

    void createRoute(Customer customer);

    void chooseCustomer(Scanner scanner);
    void completeOrder(Order order);
    void print();
    void updateCurrentOrder(Order order);
}
