package com.myProject.transportCompany.generatemodels;

import com.myProject.transportCompany.InputHandler;
import com.myProject.transportCompany.delivery.Delivery;
import com.myProject.transportCompany.delivery.deliveryStrategy.TruckDeliveryStrategy;
import com.myProject.transportCompany.delivery.deliveryStrategy.VanDeliveryStrategy;
import com.myProject.transportCompany.interfaces.IOrderManager;
import com.myProject.transportCompany.model.Customer;
import com.myProject.transportCompany.model.Location;
import com.myProject.transportCompany.model.Order;
import com.myProject.transportCompany.model.Route;
import com.myProject.transportCompany.builderObject.Vehicle;

import java.util.*;

public class OrderManager implements IOrderManager {

    private static volatile OrderManager orderInstance;
    List<Order> ordersHistory;
    Order currentOrder;
    private final List<Order> vanOrders = Collections.synchronizedList(new ArrayList<>());
    private final Map<Vehicle, List<Order>> truckQueues = new HashMap<>();

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
    public void printOrderHistory() {
        ordersHistory.forEach(System.out::println);
        System.out.println(!ordersHistory.isEmpty() ? "" : ordersHistory.size() + " orders have been added." );
    }

    @Override
    public void updateCurrentOrder(Order order){
        this.currentOrder = order;
    }
    @Override
    public void discussOrder(Integer... choice) {
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

        if(choice == null){
            Scanner scanner = new Scanner(System.in);
            Map<String, Runnable> orderMenu = new LinkedHashMap<>();
            orderMenu.put("Do you want to complete the order without negotiating ", () -> completeOrder(currentOrder));
            orderMenu.put("Let's negotiate ", () -> RouteManager.getRouteInstance().negotiatingMenu(currentOrder));
            InputHandler.runMainMenu(scanner, orderMenu);
        } else {
            if(choice[0] == 1){
                completeOrder(currentOrder);
            } else if(choice[0] == 2){
                RouteManager.getRouteInstance().negotiatingMenu(currentOrder);
            } else {
                System.out.println("Do not know what to do with the order");
            }
        }

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
        VehicleManagerBuilder.getInstance().updateVehicle(order);
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
        Customer customer = CustomerManager.getInstance().getOneCustomer(choice);;

        while(customer == null){
            System.out.printf("\nCustomer %s not found, enter name again:  ", choice);
            choice = scanner.nextLine();
            customer = CustomerManager.getInstance().getOneCustomer(choice);
        }
        System.out.printf("\nCustomer '%s' has been selected.\n", customer.getName());
        createDelivery(customer);
    }

    @Override
    public void createDelivery(Customer customer) {
        String [] part = customer.getDestinationInfo().split(" ");
        Location start = new Location(part[0]);
        Location end = new Location(part[1]);

        //routes distance gets created in RouteManager.getStraightDistances(start, destination);
        Route route = new Route.Builder()
                .start(start)
                .destination(end)
                .distance(start, end)
                .build();

        //price gets created Order.calculateTotal() & vehicle gets assigned, who will deliver the delivery
        Optional<Order> newOrder = new Order.Builder()
                .customer(customer)
                .route(route)
                .id()//ids are always after customer, because id is created with the name of customer
                .build();

        if(newOrder.isEmpty()){
            customer.updateComplaintCount(+1);
            System.out.println("Sorry, I can not take your order!");
            return;
        }
        newOrder.ifPresent(order -> {

            Vehicle vehicle = order.getVehicle();
            Delivery delivery;

            if (vehicle.getType().equalsIgnoreCase("van")) {
                delivery = new Delivery(order, customer, route, "package", new VanDeliveryStrategy());
            } else {
                delivery = new Delivery(order, customer, route, "package", new TruckDeliveryStrategy(vehicle.getCapacity()));
            }

            ordersHistory.add(newOrder.get());
            delivery.startDelivery();
        });

    }

    private void runOrderMenu() {
        Scanner scanner = new Scanner(System.in);
        Map<String, Runnable> orderMenu = new LinkedHashMap<>();
        if(currentOrder != null){
            System.out.println("You have order to complete");
        }
        orderMenu.put("Choose customer", () -> chooseCustomer(scanner));
        orderMenu.put("This is how numbers look like: ", this::discussOrder);

        InputHandler.runMainMenu(scanner, orderMenu);
    }

    private void startAutoSettingOrder() {

        Thread autoOrderThread = new Thread(() -> {

            while(!Thread.currentThread().isInterrupted()){
                try{
                    Thread.sleep(750);
                } catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                    break;
                }
                Random random = new Random();
                CustomerManager.getInstance().getList()
                        .forEach(customer -> {
                            createDelivery(customer);
                            discussOrder(random.nextInt(2) + 1);
                        });

            }
        });
        autoOrderThread.setDaemon(true);
        autoOrderThread.start();

        Thread updater = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(5000); // 5s refresh
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
        updater.setDaemon(true); // so it won’t block program exit
        updater.start();
    }

    @Override
    public void initiateOrderManager() {
        startAutoSettingOrder();
        runOrderMenu();
    }
}
