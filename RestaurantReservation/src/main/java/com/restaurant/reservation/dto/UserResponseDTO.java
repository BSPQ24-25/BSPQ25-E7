package com.restaurant.reservation.dto;

import com.restaurant.reservation.model.UserType;

/**
 * Data Transfer Object used to send user data in API responses.
 */
public class UserResponseDTO {

    private Long id;             /**< User's unique identifier. */
    private String email;        /**< User's email address. */
    private String username;     /**< User's username. */
    private String phone;        /**< User's phone number. */
    private UserType userType;   /**< User type (e.g., ADMIN or CUSTOMER). */

    /**
     * Constructs a UserResponseDTO with user information.
     * @param id User ID
     * @param email Email address
     * @param username Username
     * @param phone Phone number
     * @param userType Type of user
     */
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
