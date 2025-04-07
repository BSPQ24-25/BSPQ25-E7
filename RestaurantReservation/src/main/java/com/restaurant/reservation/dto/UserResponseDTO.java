package com.restaurant.reservation.dto;

import com.restaurant.reservation.model.UserType;

public class UserResponseDTO {

    private Long id;
    private String email;
    private String username;
    private String phone;
    private UserType userType;

    // Constructor
    public UserResponseDTO(Long id, String email, String username, String phone, UserType userType) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.userType = userType;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public UserType getUserType() {
        return userType;
    }
}
