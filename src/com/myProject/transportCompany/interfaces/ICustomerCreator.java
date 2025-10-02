package com.myProject.transportCompany.interfaces;

import com.myProject.transportCompany.model.Customer;
import com.myProject.transportCompany.model.Order;

import java.util.List;

public interface ICustomerCreator {

    void generateCustomers();
    List<Customer> getList();
    void createCustomer(Customer customer);
    public void print();
    void printCustomersInTheQueue();
    Customer getOneCustomer(String customerName);
    void updateCustomer(Order order);
}
