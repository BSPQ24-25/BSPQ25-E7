package com.restaurant.reservation.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    private String adminLevel; 
    private String department;
    
    // Getters y Setters
    public String getAdminLevel() {
        return adminLevel;
    }
    
    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public void manageRestaurantSettings(Restaurant restaurant) {
    }
}