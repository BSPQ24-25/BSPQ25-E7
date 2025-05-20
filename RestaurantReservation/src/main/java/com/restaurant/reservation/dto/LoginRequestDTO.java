package com.restaurant.reservation.dto;

<<<<<<< HEAD
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
=======
/**
 * @class LoginRequestDTO
 * @brief DTO de entrada para la autenticación de usuarios.
 */
public class LoginRequestDTO {
    private String email;
>>>>>>> 2fa7554a80bfbc37e8cdf443ca991ed3b9ef4758
    private String password;

    public LoginRequestDTO() {}

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters y setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
