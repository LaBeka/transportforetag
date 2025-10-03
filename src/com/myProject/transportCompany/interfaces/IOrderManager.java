package com.myProject.transportCompany.interfaces;

import com.myProject.transportCompany.model.Customer;
import com.myProject.transportCompany.model.Order;

import java.util.Scanner;

public interface IOrderManager {

    void initiateOrderManager();

    void discussOrder(Integer... choice);

    void createDelivery(Customer customer);

    void chooseCustomer(Scanner scanner);
    void completeOrder(Order order);
    void printOrderHistory();
    void updateCurrentOrder(Order order);

}
