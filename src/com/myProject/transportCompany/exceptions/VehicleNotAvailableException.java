package com.myProject.transportCompany.exceptions;

public class VehicleNotAvailableException extends IllegalStateException {
    public VehicleNotAvailableException(String message) {
        super(message);
    }
}
