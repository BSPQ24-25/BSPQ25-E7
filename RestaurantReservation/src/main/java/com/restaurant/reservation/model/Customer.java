package com.restaurant.reservation.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("customer")
public class Customer extends User {

    // Constructor vacío
    public Customer() {}

    // Constructor con parámetros
    public Customer(String gmail, String name, String phone, String password) {
        super(gmail, name, phone, password);
    }

    // Métodos adicionales si fueran necesarios
}
