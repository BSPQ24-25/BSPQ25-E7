package com.restaurant.reservation;

import com.restaurant.reservation.dto.UserRegistrationDTO;
import com.restaurant.reservation.model.Customer;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.repository.UserRepository;
import com.restaurant.reservation.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void testRegisterUser_Success() {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("1234");
        dto.setName("David");
        dto.setPhone("123456789");

        when(userRepository.existsByUsername("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("1234")).thenReturn("encodedPassword");

        User mockUser = new Customer();
        mockUser.setUsername("test@example.com");
        mockUser.setPassword("encodedPassword");
        mockUser.setName("David");
        mockUser.setPhone("123456789");

        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        User registered = userService.registerUser(dto);

        assertNotNull(registered);
        assertEquals("test@example.com", registered.getUsername());
        assertEquals("encodedPassword", registered.getPassword());
    }

    @Test
    void testFindByUsername() {
        User user = new Customer();
        user.setUsername("test");

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));

        Optional<User> found = userService.findByUsername("test");

        assertTrue(found.isPresent());
        assertEquals("test", found.get().getUsername());
    }
}
