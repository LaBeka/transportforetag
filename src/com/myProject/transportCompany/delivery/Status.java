package com.myProject.transportCompany.delivery;

public enum Status {
    START("start"),
    IN_PROGRESS("inProgress"),
    COMPLETED("completed"),
    CANCELLED("cancelled"),
    DELAYED("delayed");

    private String status;
    Status(String status) {
        this.status = status;
    }
}
