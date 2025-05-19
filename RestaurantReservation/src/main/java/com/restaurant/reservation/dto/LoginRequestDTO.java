package com.restaurant.reservation.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object used for user login requests.
 * Contains login credentials (email and password).
 */
public class LoginRequestDTO {

    /**
     * User's email address.
     */
    @NotBlank
    private String email;

    /**
     * User's password.
     */
    @NotBlank
    private String password;

    // Getters y setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email != null ? email.trim().toLowerCase() : null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
