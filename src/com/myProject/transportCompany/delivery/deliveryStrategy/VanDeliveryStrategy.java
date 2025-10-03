package com.myProject.transportCompany.delivery.deliveryStrategy;

import com.myProject.transportCompany.delivery.Delivery;
import com.myProject.transportCompany.delivery.Status;
import com.myProject.transportCompany.generatemodels.OrderManager;

public class VanDeliveryStrategy implements DeliveryStrategy {
    @Override
    public void executeDelivery(Delivery delivery) {
        System.out.printf("Van assigned. Delivery for customer %s is starting immediately.%n",
                delivery.getCustomer().getName());

        delivery.setStatus(Status.IN_PROGRESS);

        // simulate delivery logic
        System.out.printf("Van is delivering from %s to %s%n",
                delivery.getRoute().getStart().getCity(),
                delivery.getRoute().getDestination().getCity());

        // after route
        delivery.setStatus(Status.COMPLETED);
        System.out.printf("Delivery completed by Van. Order: %s%n", delivery.getOrder().toString());
        OrderManager.getInstance().completeOrder(delivery.getOrder());
    }
}
