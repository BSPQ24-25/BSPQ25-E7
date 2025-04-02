package com.restaurant.reservation.model;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurant_table")
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // este es el PK autoincremental
    private Long id;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private String state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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