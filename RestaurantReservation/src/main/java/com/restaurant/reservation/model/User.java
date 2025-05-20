package com.restaurant.reservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Represents a user of the system.
<<<<<<< HEAD
 * A user can be an admin or a customer, identified by a unique email.
=======
 * A user can be either an admin or a customer, identified by email.
>>>>>>> 2fa7554a80bfbc37e8cdf443ca991ed3b9ef4758
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
<<<<<<< HEAD
     * Email address of the user.
     * Must follow valid email format.
     */
    @NotBlank(message = "Email es obligatorio")
=======
     * User email (must be unique and valid format).
     */
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
>>>>>>> 2fa7554a80bfbc37e8cdf443ca991ed3b9ef4758
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    /**
     * Username of the user.
     */
<<<<<<< HEAD
    @NotBlank(message = "Nombre de usuario es obligatorio")
=======
    @NotBlank
>>>>>>> 2fa7554a80bfbc37e8cdf443ca991ed3b9ef4758
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    /**
     * Phone number of the user.
     */
<<<<<<< HEAD
    @NotBlank(message = "Teléfono es obligatorio")
=======
    @NotBlank
    @Pattern(regexp = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$")
>>>>>>> 2fa7554a80bfbc37e8cdf443ca991ed3b9ef4758
    @Column(name = "phone", nullable = false, length = 50)
    private String phone;

    /**
<<<<<<< HEAD
     * User password. Should be stored encrypted.
     */
    @NotBlank(message = "Contraseña es obligatoria")
=======
     * Password for the account.
     */
    @NotBlank
>>>>>>> 2fa7554a80bfbc37e8cdf443ca991ed3b9ef4758
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    /**
<<<<<<< HEAD
     * Defines the type of user (e.g., ADMIN or CUSTOMER).
=======
     * Role of the user in the system (e.g. ADMIN, CUSTOMER).
>>>>>>> 2fa7554a80bfbc37e8cdf443ca991ed3b9ef4758
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    // Constructores
    public User() {}

    public User(String email, String username, String phone, String password, UserType userType) {
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.userType = userType;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email != null ? email.trim().toLowerCase() : null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", email='" + email + '\'' +
               ", username='" + username + '\'' +
               ", phone='" + phone + '\'' +
               ", userType=" + userType +
               '}';
    }
}
