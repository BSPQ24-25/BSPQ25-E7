package com.restaurant.reservation.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a reservation made by a user for a specific table and time.
 */
@Entity
@Table(name = "reservation")
public class Reservation {

    /**
     * Unique identifier for the reservation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    /**
     * The user who made the reservation.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The table reserved.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", nullable = false)
    private RestaurantTable table;

    /**
     * The date of the reservation.
     */
    @Column(nullable = false)
    private LocalDate date;

    /**
     * The time of the reservation.
     */
    @Column(nullable = false)
    private LocalTime hour;

    /**
     * Number of people included in the reservation.
     */
    @Column(nullable = false, name = "n_people")
    private int nPeople;

    /**
     * The state of the reservation (e.g., confirmed).
     */
    @Column(nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'confirmed'")
    private String state;


    // Constructores
    public Reservation() {}

    public Reservation(User user, RestaurantTable table, 
                      LocalDate date, LocalTime hour, int nPeople) {
        this.user = user;
        this.table = table;
        this.date = date;
        this.hour = hour;
        this.nPeople = nPeople;
        this.state = "confirmed";
    }

    // Getters y setters
    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RestaurantTable getTable() {
        return table;
    }

    public void setTable(RestaurantTable table) {
        this.table = table;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getId() {
        return this.reservationId;
    }
}
