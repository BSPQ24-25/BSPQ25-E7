package com.restaurant.reservation.model;

import jakarta.persistence.*;

/**
 * Represents a table within a restaurant.
 * Each table has a capacity and a state (e.g., available, reserved).
 */
@Entity
@Table(name = "restaurant_table")
public class RestaurantTable {

    /**
     * Unique identifier for the table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "table_id")
    private Integer tableId;

    /**
     * Number of people the table can accommodate.
     */
    @Column(nullable = false)
    private Integer capacity;

    /**
     * Current state of the table (e.g., available, occupied).
     */
    @Column(nullable = false)
    private String state;

    /**
     * The restaurant to which the table belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    // Getters y Setters
    public Integer getId() {
        return tableId;
    }

    public void setId(Integer tableId) {
        this.tableId = tableId;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
