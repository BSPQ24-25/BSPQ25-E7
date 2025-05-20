package com.restaurant.reservation.model;

import jakarta.persistence.*;
import java.util.List;

/**
<<<<<<< HEAD
 * Represents a restaurant.
 * Contains its capacity, available hours, and its tables.
=======
 * Represents a restaurant with tables and availability.
>>>>>>> 2fa7554a80bfbc37e8cdf443ca991ed3b9ef4758
 */
@Entity
public class Restaurant {

    /**
     * Unique identifier for the restaurant.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    /**
     * Name of the restaurant.
     */
    @Column(nullable = false)
    private String name;

    /**
<<<<<<< HEAD
     * List of availability hours for the restaurant.
=======
     * List of availability hours.
>>>>>>> 2fa7554a80bfbc37e8cdf443ca991ed3b9ef4758
     */
    @ElementCollection
    @CollectionTable(
        name = "restaurant_availability_hours",
        joinColumns = @JoinColumn(name = "restaurant_id")
    )
    @Column(name = "availability_hours")
    private List<String> availabilityHours;

    /**
<<<<<<< HEAD
     * Total seating capacity of the restaurant.
=======
     * Total capacity of the restaurant.
>>>>>>> 2fa7554a80bfbc37e8cdf443ca991ed3b9ef4758
     */
    @Column(nullable = false)
    private Integer capacity;

    /**
<<<<<<< HEAD
     * List of tables available in the restaurant.
=======
     * List of tables in the restaurant.
>>>>>>> 2fa7554a80bfbc37e8cdf443ca991ed3b9ef4758
     */
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RestaurantTable> tables;


    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAvailabilityHours() {
        return availabilityHours;
    }

    public void setAvailabilityHours(List<String> availabilityHours) {
        this.availabilityHours = availabilityHours;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<RestaurantTable> getTables() {
        return tables;
    }

    public void setTables(List<RestaurantTable> tables) {
        this.tables = tables;
    }
}