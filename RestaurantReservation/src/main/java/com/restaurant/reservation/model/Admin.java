package com.restaurant.reservation.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {

    // Constructor vacío
    public Admin() {}

    // Constructor con parámetros
    public Admin(String gmail, String name, String phone, String password) {
        super(gmail, name, phone, password);
    }

    // Métodos adicionales si fueran necesarios
}
