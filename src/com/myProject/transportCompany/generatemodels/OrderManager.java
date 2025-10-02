package com.myProject.transportCompany.generatemodels;

import com.myProject.transportCompany.InputHandler;
import com.myProject.transportCompany.interfaces.IOrderManager;
import com.myProject.transportCompany.model.Customer;
import com.myProject.transportCompany.model.Location;
import com.myProject.transportCompany.model.Order;
import com.myProject.transportCompany.model.Route;
import com.myProject.transportCompany.model.vehicle.Vehicle;

import java.util.*;

public class OrderManager implements IOrderManager {

    List<Order> ordersHistory;
    Order currentOrder;
    private static volatile OrderManager orderInstance;

    private OrderManager() {
        this.ordersHistory = new ArrayList<>();
    }

    public static OrderManager getInstance() {
        if (orderInstance == null) {
            synchronized (OrderManager.class) {
                if (orderInstance == null) {
                    orderInstance = new OrderManager();
                }
            }
        }
        return orderInstance;
    }

    @Override
    public void initiateOrderManager(List<Customer> customers, List<Vehicle> vehicles) {
        runOrderMenu();
    }

    @Override
    public void print() {
        for (Order o : ordersHistory) {
            System.out.println(o.toString());
        }
        System.out.println();
    }

    @Override
    public void updateCurrentOrder(Order order){
        this.currentOrder = order;
    }
    @Override
    public void discussOrder() {
        if(currentOrder == null) {
            System.out.println("Nothing to discuss! Take order of next customer!");
            return;
        }
        if(!currentOrder.checkIfVehicle()){
            System.out.println("Sorry, we could not find right vehicle for delivery. Try again later.");
            currentOrder = null;
            return;
        }
        System.out.printf("Let's start negotiate the order. Currently, the price to pay: %.2f%n" , currentOrder.getPrice());

        Scanner scanner = new Scanner(System.in);
        Map<String, Runnable> orderMenu = new LinkedHashMap<>();
        orderMenu.put("Do you want to complete", () -> completeOrder(currentOrder));
        orderMenu.put("Let's negotiate ", () -> RouteManager.getRouteInstance().negotiatingMenu(currentOrder));

        InputHandler.runMainMenu(scanner, orderMenu);
    }

    @Override
    public void completeOrder(Order order) {
        if(currentOrder == null){
            System.out.println("Choose a customer ");
            return;
        }
        System.out.printf("Delivery is completed. Total price paid: %.2f%n%n" , order.getPrice());
        ordersHistory.add(order);
        order.setCompleted(true);

        CustomerManager.getInstance().updateCustomer(order);
        VehicleManager.getInstance().updateVehicle(order);
        currentOrder = null;
    }


    @Override
    public void chooseCustomer(Scanner scanner) {
        if(currentOrder != null){
            System.out.println("Finish the deal with customer " + currentOrder.getCustomer().getName());
            return;
        }
        System.out.print("Enter customer name:");
        String choice = scanner.nextLine();
        Customer customer = CustomerManager.getInstance().getOneCustomer(choice);

        if(customer == null){
            System.out.println("Customer " + choice + " not found");
            chooseCustomer(scanner);
        }
        System.out.println(STR."Customer \{customer.toString()} has been selected.\n");
        createRoute(customer);
    }

    @Override
    public void createRoute(Customer customer) {
        String [] part = customer.getDestinationInfo().split(" ");
        Location start = new Location(part[0]);
        Location end = new Location(part[1]);

        //routes distance gets created in RouteManager.getStraightDistances(start, destination);
        Route route = new Route.Builder()
                .start(start)
                .destination(end)
                .distance(start, end)
                .build();

        //price gets created Order.calculateTotal()
        currentOrder = new Order.Builder()
                .customer(customer)
                .route(route)
                .build();
    }

    private void runOrderMenu() {
        Scanner scanner = new Scanner(System.in);
        Map<String, Runnable> orderMenu = new LinkedHashMap<>();
        orderMenu.put("Choose customer", () -> chooseCustomer(scanner));
        orderMenu.put("This is how numbers look like: ", this::discussOrder);

        InputHandler.runMainMenu(scanner, orderMenu);
    }
}
