package com.myProject.transportCompany.generatemodels;

import com.myProject.transportCompany.interfaces.ICustomerCreator;
import com.myProject.transportCompany.model.Customer;
import com.myProject.transportCompany.model.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerManager implements ICustomerCreator {

    List<Customer> customers = new ArrayList<>();
    private static volatile CustomerManager customerInstance;

    private CustomerManager() {
        generateCustomers();//will run only once when instance is created line 24
    }

    public static CustomerManager getInstance() {
        if (customerInstance == null) {
            synchronized (CustomerManager.class) {
                if (customerInstance == null) {
                    customerInstance = new CustomerManager();
                }
            }
        }
        return customerInstance;
    }

    @Override
    public void generateCustomers() {
        createCustomer(new Customer("Customer One", "Stockholm Malmo", "fast package one(furniture)", 200.05));
        createCustomer(new Customer("Customer Two", "Orebro Uppsala", "slow package two(kitchen utils)", 100.05));
        createCustomer(new Customer("Customer Three", "Uppsala Orebro", "fast package three(kitchen utils)", 155.05));
        createCustomer(new Customer("Customer Four", "Gothenburg Malmo", "slow package four(glass)", 19.05));
        createCustomer(new Customer("Customer Five","Uppsala Stockholm", "slow package five(furniture)", 250.05));
        createCustomer(new Customer("Customer Six", "Orebro Malmo", "fast package six(documents)", 5.05));
        createCustomer(new Customer("Customer Seven", "Orebro Gothenburg", "fast package seven(sofa)", 221.05));
        createCustomer(new Customer("Customer Eight", "Malmo Stockholm", "slow package eight(kitchen utils)", 89.05));
        createCustomer(new Customer("Customer Nine", "Uppsala Stockholm", "slow package nine(furniture)", 159.05));
        createCustomer(new Customer("Customer Ten","Malmo Gothenburg", "fast package ten(sofa)", 288.05));
    }

    @Override
    public List<Customer> getList(){
        return Collections.unmodifiableList(customers);
    }

    @Override
    public void createCustomer(Customer customer) {
        this.customers.add(customer);
    }

    @Override
    public void print() {
        customers.forEach(System.out::println);
        System.out.println();
    }
    @Override
    public void printPendingCustomers() {
        System.out.println("Take order of these customers:");
        customers.forEach(c ->  System.out.println(c.toString()));
        System.out.println();
    }

    @Override
    public Customer getOneCustomer(String customerName) {
        return customers.stream()
                .filter(c -> c.getName().equalsIgnoreCase(customerName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void updateCustomer(Order order){
        customers.stream()
                .filter( c -> c.equals(order.getCustomer()))
                .findFirst()
                .ifPresent(customer -> customer.setServed(true));
    }
}
