package com.restaurant.reservation.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {
    private String phone;  
    private String customerType; 
    
    @OneToMany(mappedBy = "customer")
    private List<Reservation> reservations;
    
    // Getters y Setters
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getCustomerType() {
        return customerType;
    }
    
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
    
    public List<Reservation> getReservations() {
        return reservations;
    }
    
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Customer{" +
               "id=" + getId() +
               ", username='" + getUsername() + '\'' +
               ", phone='" + phone + '\'' +
               ", customerType='" + customerType + '\'' +
               '}';
    }
}