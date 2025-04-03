package com.restaurant.reservation.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurantId")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ElementCollection
    @CollectionTable(
        name = "restaurant_availability_hours",
        joinColumns = @JoinColumn(name = "restaurant_id")
    )
    @Column(name = "availability_hours")
    private List<String> availabilityHours;

    @Column(nullable = false)
    private Integer capacity;

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