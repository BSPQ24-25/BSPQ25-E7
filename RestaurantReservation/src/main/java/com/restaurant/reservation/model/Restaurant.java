package com.restaurant.reservation.model;

import jakarta.persistence.*;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  
    @Column(name = "id")
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
        
    private Long restaurantId;

    private String name;

    private String availabilityHours;

    private int capacity;

    @OneToMany(mappedBy = "restaurant")
    private List<RestaurantTable> tables;

    // Getters y setters
    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;

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

    public String getAvailabilityHours() {
        return availabilityHours;
    }

    public void setAvailabilityHours(String availabilityHours) {
        this.availabilityHours = availabilityHours;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
    

}
