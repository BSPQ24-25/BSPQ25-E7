package com.restaurant.reservation.dto;

import com.restaurant.reservation.model.UserType;

/**
 * @class UserResponseDTO
 * @brief DTO de salida para representar datos públicos de un usuario.
 */

public class UserResponseDTO {

    private Long id;
    private String email;
    private String username;
    private String phone;
    private UserType userType;

    /**
     * @brief Constructor con todos los campos.
     * @param id ID del usuario.
     * @param email Email del usuario.
     * @param username Nombre de usuario.
     * @param phone Teléfono del usuario.
     * @param userType Tipo de usuario (ADMIN, CUSTOMER).
     */

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
