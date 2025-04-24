package com.restaurant.reservation.exception;

public class PastDateReservationException extends RuntimeException{
    public PastDateReservationException() {
        super("You can't make a reservation for a past date");
    }
}
