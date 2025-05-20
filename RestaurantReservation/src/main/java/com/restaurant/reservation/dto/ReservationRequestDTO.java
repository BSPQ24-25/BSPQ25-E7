package com.restaurant.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @class ReservationRequestDTO
 * @brief DTO de entrada para crear o actualizar una reserva.
 */
public class ReservationRequestDTO {
    private LocalDate date;
    private LocalTime hour;
    private int nPeople;

    public ReservationRequestDTO() {}

    /**
     * @brief Constructor con parámetros.
     * @param date Fecha de la reserva.
     * @param hour Hora de la reserva.
     * @param nPeople Número de personas.
     */
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
    
    /**
     * @brief Verifica si la hora de la reserva es válida (en punto o y media).
     * @return true si es válida, false si no.
     */
    public boolean isValidTime() {
        int minute = hour.getMinute();
        return minute == 0 || minute == 30;
    }
}