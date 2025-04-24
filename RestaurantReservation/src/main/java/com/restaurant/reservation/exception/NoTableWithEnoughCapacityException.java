package com.restaurant.reservation.exception;

public class NoTableWithEnoughCapacityException extends RuntimeException {
    public NoTableWithEnoughCapacityException(int requestedCapacity) {
        super("No table available for " + requestedCapacity + " people.");
    }
}