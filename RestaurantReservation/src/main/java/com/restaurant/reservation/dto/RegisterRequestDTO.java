package com.restaurant.reservation.dto;

import com.restaurant.reservation.model.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class RegisterRequestDTO {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Formato de email inválido")
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    @Pattern(regexp = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$", message = "Formato de teléfono inválido")
    private String phone;

    @NotBlank
    private String password;

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


    public RegisterRequestDTO(String email, String username, String phone, String password, UserType userType) {
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.userType = userType;
    }
}
