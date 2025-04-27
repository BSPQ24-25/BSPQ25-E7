package com.restaurant.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequestDTO {
    private LocalDate date;
    private LocalTime hour;
    private int nPeople;

    public ReservationRequestDTO() {}

    public ReservationRequestDTO(LocalDate date, LocalTime hour, int nPeople) {
        this.date = date;
        this.hour = hour;
        this.nPeople = nPeople;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public int getnPeople() {
        return nPeople;
    }

    public void setnPeople(int nPeople) {
        this.nPeople = nPeople;
    }
    
    public boolean isValidTime() {
        int minute = hour.getMinute();
        return minute == 0 || minute == 30;
    }
}