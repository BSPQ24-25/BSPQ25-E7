package com.restaurant.reservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@jakarta.persistence.Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "table_id", nullable = false)
    private Table table;

    @NotNull
    @Future(message = "La fecha debe ser en el futuro")
    private LocalDateTime dateTime;

    @Min(value = 1, message = "Debe haber al menos 1 persona")
    private int nPeople;

    @Enumerated(EnumType.STRING)
    private ReservationState state;

    public enum ReservationState {
        PENDING, CONFIRMED, CANCELLED
    }


       // Getters y setters...

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getnPeople() {
        return nPeople;
    }

    public void setnPeople(int nPeople) {
        this.nPeople = nPeople;
    }

    public ReservationState getState() {
        return state;
    }

    public void setState(ReservationState state) {
        this.state = state;
    }

 



    
}
