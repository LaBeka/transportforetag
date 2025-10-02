package com.myProject.transportCompany.delivery;

public enum Status {
    START("start"),
    ON_GOING("onGoing"),
    DELIVERED("delivered"),
    CANCELLED("cancelled"),
    DELAYED("delayed");

    private String status;
    Status(String status) {
        this.status = status;
    }
}
