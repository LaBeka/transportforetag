package com.myProject.transportCompany.generatemodels;

import com.myProject.transportCompany.builderObject.Truck;
import com.myProject.transportCompany.builderObject.Van;
import com.myProject.transportCompany.delivery.Delivery;
import com.myProject.transportCompany.delivery.deliveryStrategy.TruckDeliveryStrategy;
import com.myProject.transportCompany.delivery.deliveryStrategy.VanDeliveryStrategy;
import com.myProject.transportCompany.model.Customer;
import com.myProject.transportCompany.model.Location;
import com.myProject.transportCompany.model.Order;
import com.myProject.transportCompany.model.Route;
import com.myProject.transportCompany.builderObject.Vehicle;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AutoOrderManager {

    private static volatile AutoOrderManager instance;
    List<Order> ordersHistory;
    private final Map<Vehicle, List<Order>> truckQueues = new ConcurrentHashMap<>();

    private AutoOrderManager() {
        this.ordersHistory = new CopyOnWriteArrayList<>();
    }

    public static AutoOrderManager getInstance() {
        if (instance == null) {
            synchronized (AutoOrderManager.class) {
                if (instance == null) {
                    instance = new AutoOrderManager();
                }
            }
        }
        return instance;
    }

    public void printOrderHistory() {
        ordersHistory.forEach(System.out::println);
        System.out.println(!ordersHistory.isEmpty() ? "" : ordersHistory.size() + " orders have been added." );
    }

    public void discussOrder(int choice, Order currentOrder) {
        System.out.printf("Let's start negotiate the order. Currently, the price to pay: %.2f%n" , currentOrder.getPrice());

        if(choice == 1){
            completeOrder(currentOrder);
        } else if(choice == 2){
            RouteManager.getRouteInstance().expensiveOrder(currentOrder);
        } else if(choice == 3){
            RouteManager.getRouteInstance().cheapOrder(currentOrder);
        } else {
            System.out.println("Do not know what to do with the order");
        }
    }

    public void completeOrder(Order order) {
        System.out.printf("Delivery is completed. Total price paid: %.2f%n%n" , order.getPrice());
        ordersHistory.add(order);
        order.setCompleted(true);

        CustomerManager.getInstance().updateCustomer(order);
        VehicleManagerBuilder.getInstance().updateVehicle(order);
    }

    public Optional<Order> createDelivery(Customer customer) {
        String [] part = customer.getDestinationInfo().split(" ");
        Location start = new Location(part[0]);
        Location end = new Location(part[1]);

        //routes distance gets created in RouteManager.getStraightDistances(start, destination);
        Route route = new Route.Builder().start(start).destination(end).distance(start, end).build();

        //price gets created Order.calculateTotal() & vehicle gets assigned, who will deliver the delivery
        //ids are always after customer, because id is created with the name of customer
        Optional<Order> newOrder = new Order.Builder().customer(customer).route(route).id().build();

        if(newOrder.isEmpty()){
            customer.updateComplaintCount(+1);
            System.out.println("Sorry, I can not take your order!");
            return Optional.empty();
        }
        newOrder.ifPresent(order -> {

            Vehicle vehicle = order.getVehicle();
            Delivery delivery = null;

            if (vehicle instanceof Van) {
                delivery = new Delivery(order, customer, route, "package", new VanDeliveryStrategy());
                delivery.startDelivery();

            } else if(vehicle instanceof Truck t){
                truckQueues.computeIfAbsent(t, v -> new ArrayList<>()).add(order);
                System.out.printf("Order queued for truck %s. Current load: %.2f/%s%n",
                        vehicle.getRegNumber(),
                        truckQueues.get(vehicle).stream().mapToDouble(o -> o.getCustomer().getWeight()).sum(),
                        t.getCapacity());
            }
            ordersHistory.add(newOrder.get());

        });
        return newOrder;
    }

    private static Random random = new Random();

    private void startAuto() {
        Thread thread = new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()){
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                    break;
                }
                CustomerManager.getInstance().getList()
                        .forEach(customer -> {
                            Optional<Order> newOrder;
                            if(customer.getPackageInfo().split(" ")[0].equalsIgnoreCase("fast")){
                                newOrder = createDelivery(customer);
                                newOrder.ifPresent(order -> {
                                    if(order.getVehicle() instanceof Van) {
                                        discussOrder(random.nextInt(3) + 1, newOrder.get());
                                    }
                                });
                            } else {
                                newOrder = createDelivery(customer);
                            }
                        });
            }
        });
        thread.start();
    }

    private void startAutoTruckOrder(){
        Thread truckThread = new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()){
                try{
                    Thread.sleep(2500);
                } catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                    break;
                }
                for(Map.Entry<Vehicle, List<Order>> entry : truckQueues.entrySet()){
                    Vehicle truck = entry.getKey();
                    List<Order> queuedOrders = entry.getValue();

                    double totalLoad = queuedOrders.stream()
                            .mapToDouble(o -> o.getCustomer().getWeight())
                            .sum();

//                    TODO REWRITE ALL DOWNCASTINGS: 1 Polymorphic API on Vehicle: expose the concept on Vehicle (e.g., Optional<Integer> capacity()), override it in Truck, and let Van return empty, avoiding casts and instanceof in client code.
//                    TODO 2 Visitor pattern: add Vehicle.accept(visitor) with visitTruck/visitVan so Truck‑specific data is obtained via double dispatch without downcasting.
                    if(totalLoad > ((Truck)truck).getCapacity() || !queuedOrders.isEmpty()){
                        System.out.printf("Truck %s departing with %d orders (%.2f/%s capacity).%n",
                                truck.getRegNumber(), queuedOrders.size(), totalLoad, ((Truck)truck).getCapacity());

                        //should LOOOOP List<Order> queuedOrders
                        Customer customer = queuedOrders.get(0).getCustomer();
                        String [] part = customer.getDestinationInfo().split(" ");
                        Location start = new Location(part[0]);
                        Location end = new Location(part[1]);
                        Route route = new Route.Builder().start(start).destination(end).distance(start, end).build();
                        Optional<Order> newOrder = new Order.Builder().customer(customer).route(route).id().build();
                        Delivery delivery = new Delivery(newOrder.get(), customer, route, "package", new TruckDeliveryStrategy(totalLoad));
                        delivery.startDelivery();

                        discussOrder(random.nextInt(3) + 1, newOrder.get());

                        queuedOrders.forEach(o -> {
                            o.setCompleted(true);
                            ordersHistory.add(o);
                            CustomerManager.getInstance().updateCustomer(o);
                        });
                        queuedOrders.clear(); //reset truck queue
                    }
                }
            }
        });
        truckThread.start();
    }

    private void statusUpdaterThread(){
        Thread updater = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(10000); // 5s refresh
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                // Print status of vehicles + deliveries
                System.out.println("[Auto Update]");
                VehicleManagerBuilder.getInstance().printAvailableVehicles();
                CustomerManager.getInstance().printPendingCustomers();
                printOrderHistory();
            }
        });
        updater.start();
    }
    public void runAutoOrderManager() {

        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(this::startAuto);
        executor.submit(this::startAutoTruckOrder);
        executor.submit(this::statusUpdaterThread);

        executor.shutdown();
    }
}
