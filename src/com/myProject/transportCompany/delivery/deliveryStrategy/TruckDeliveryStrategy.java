package com.myProject.transportCompany.delivery.deliveryStrategy;

import com.myProject.transportCompany.delivery.Delivery;
import com.myProject.transportCompany.delivery.Status;
import com.myProject.transportCompany.generatemodels.AutoOrderManager;

import java.util.*;

public class TruckDeliveryStrategy implements DeliveryStrategy {
    private final double maxCapacity;
    private double currentLoad = 0;
    private final List<Delivery> queuedDeliveries = new ArrayList<>();

    private static int count=0;
    public TruckDeliveryStrategy(double maxCapacity) {
        count++;
        System.out.printf("new TruckDeliveryStrategy %s %.2f%n" , count, maxCapacity);
        this.maxCapacity = maxCapacity;
    }

    @Override
    public void executeDelivery(Delivery delivery) {
        queuedDeliveries.add(delivery);
        currentLoad += delivery.getCustomer().getWeight();

        System.out.printf("Truck queued order from %s. Current load: %.2f/%.2f%n",
                delivery.getCustomer().getName(), currentLoad, maxCapacity);

        if (currentLoad >= maxCapacity) {
            System.out.println("Truck is full! Starting delivery route...");
            for (Delivery d : queuedDeliveries) {
                d.setStatus(Status.IN_PROGRESS);
                System.out.printf("Delivering order for customer %s from %s to %s%n",
                        d.getCustomer().getName(),
                        d.getRoute().getStart().getCity(),
                        d.getRoute().getDestination().getCity());
                d.setStatus(Status.COMPLETED);
            }
            queuedDeliveries.clear();
            currentLoad = 0;
//            AutoOrderManager.getInstance().completeOrder(delivery.getOrder());

        }
    }
}

