package com.restaurant.reservation.dto;

import com.restaurant.reservation.model.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
<<<<<<< HEAD
 * Data Transfer Object used to register a new user.
 * Contains user credentials and contact details.
=======
 * @class RegisterRequestDTO
 * @brief DTO de entrada para el registro de un nuevo usuario.
>>>>>>> 2fa7554a80bfbc37e8cdf443ca991ed3b9ef4758
 */
public class RegisterRequestDTO {

    /**
     * User's email address.
     * Must follow a valid email format.
     */
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Formato de email inválido")
    private String email;

    /**
     * Username of the user.
     */
    @NotBlank
    private String username;

    /**
     * Phone number of the user.
     * Must follow a valid international phone format.
     */
    @NotBlank
    @Pattern(regexp = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$", message = "Formato de teléfono inválido")
    private String phone;

    /**
     * User's password.
     */
    @NotBlank
    private String password;

    /**
     * Type of user (e.g., ADMIN or CUSTOMER).
     */
    @NotNull
    private UserType userType;
    // Getters y setters
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

    public RegisterRequestDTO() {
        
    }

    /**
     * @brief Constructor completo.
     * @param email Email del usuario.
     * @param username Nombre de usuario.
     * @param phone Teléfono del usuario.
     * @param password Contraseña.
     * @param userType Tipo de usuario (ADMIN o CUSTOMER).
     */
    public RegisterRequestDTO(String email, String username, String phone, String password, UserType userType) {
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.userType = userType;
    }
}
