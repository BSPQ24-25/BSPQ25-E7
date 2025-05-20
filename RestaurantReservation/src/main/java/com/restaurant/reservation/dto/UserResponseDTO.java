package com.restaurant.reservation.dto;

import com.restaurant.reservation.model.UserType;

/**
<<<<<<< HEAD
 * Data Transfer Object used to send user data in API responses.
 */
=======
 * @class UserResponseDTO
 * @brief DTO de salida para representar datos públicos de un usuario.
 */

>>>>>>> 2fa7554a80bfbc37e8cdf443ca991ed3b9ef4758
public class UserResponseDTO {

    private Long id;             /**< User's unique identifier. */
    private String email;        /**< User's email address. */
    private String username;     /**< User's username. */
    private String phone;        /**< User's phone number. */
    private UserType userType;   /**< User type (e.g., ADMIN or CUSTOMER). */

    /**
<<<<<<< HEAD
     * Constructs a UserResponseDTO with user information.
     * @param id User ID
     * @param email Email address
     * @param username Username
     * @param phone Phone number
     * @param userType Type of user
     */
=======
     * @brief Constructor con todos los campos.
     * @param id ID del usuario.
     * @param email Email del usuario.
     * @param username Nombre de usuario.
     * @param phone Teléfono del usuario.
     * @param userType Tipo de usuario (ADMIN, CUSTOMER).
     */

    // Constructor
>>>>>>> 2fa7554a80bfbc37e8cdf443ca991ed3b9ef4758
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
