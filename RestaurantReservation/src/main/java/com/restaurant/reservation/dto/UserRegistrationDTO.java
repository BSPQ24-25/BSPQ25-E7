package com.restaurant.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;

public class UserRegistrationDTO {
    @NotBlank
    @Size(min = 3)
    private String name;
    
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    @Pattern(regexp = "^\\+?[0-9\\s-]+$")
    private String phone;
    
    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[0-9]).*$", message = "Password must contain at least one number")
    private String password;

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}