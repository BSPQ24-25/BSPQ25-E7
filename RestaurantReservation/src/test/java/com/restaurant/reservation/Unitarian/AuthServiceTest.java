package com.restaurant.reservation.Unitarian;

import com.restaurant.reservation.dto.RegisterRequestDTO;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.model.UserType;
import com.restaurant.reservation.repository.UserRepository;
import com.restaurant.reservation.service.AuthService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private RegisterRequestDTO dto;

    @BeforeEach
    public void setup() {
        dto = new RegisterRequestDTO();
        dto.setEmail("test@email.com");
        dto.setUsername("user");
        dto.setPhone("123456789");
        dto.setPassword("password123");
        dto.setUserType(UserType.CUSTOMER);
    }

    @Test
    public void registerNewUser_success() {
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPass");

        authService.register(dto);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void registerExistingUser_shouldThrowException() {
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(new User()));
    
        assertThrows(IllegalArgumentException.class, () -> {
            authService.register(dto);
        });
    
        verify(userRepository, never()).save(any(User.class));
    }
}