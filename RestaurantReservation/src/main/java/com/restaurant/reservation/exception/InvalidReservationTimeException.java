package com.restaurant.reservation.exception;

public class InvalidReservationTimeException extends RuntimeException{
    public InvalidReservationTimeException() {
        super("The time of the reservation is not in the available hours");
    }
}
