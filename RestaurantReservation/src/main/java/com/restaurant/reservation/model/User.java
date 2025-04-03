package com.restaurant.reservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;

@Entity

@Table(name = "user") // Nombre exacto de tabla en minúsculas
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Mapeo explícito
    private Long id;

    @NotBlank(message = "Email es obligatorio")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "Nombre de usuario es obligatorio")
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @NotBlank(message = "Teléfono es obligatorio")
    @Column(name = "phone", nullable = false, length = 50)
    @Pattern(regexp = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$", message = "Formato de teléfono inválido")
    private String phone;

    @NotBlank(message = "Contraseña es obligatoria")
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    // Constructores
    public User() {}

    public User(String email, String username, String phone, String password) {
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.password = password;
    }

    // Getters y Setters mejorados
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email != null ? email.trim().toLowerCase() : null;
    }

    public String getUsername() { // Cambiado de getName() para consistencia
        return username;
    }

    public void setUsername(String username) { // Cambiado de setName()
        this.username = username != null ? username.trim() : null;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone != null ? phone.replaceAll("[^+0-9]", "") : null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Método útil para logging
    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", email='" + email + '\'' +
               ", username='" + username + '\'' +
               ", phone='" + phone + '\'' +
               ", userType='" + this.getClass().getAnnotation(DiscriminatorValue.class).value() + '\'' +
               '}';
    }
}