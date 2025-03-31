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

        // Simulando que el correo no existe en la base de datos
        when(userRepository.existsByUsername("test@example.com")).thenReturn(false);
        // Simulando que la contraseña se codifica correctamente
        when(passwordEncoder.encode("1234")).thenReturn("encodedPassword");

        // Simulamos la creación de un Customer con los datos que pasamos en el DTO
        User mockUser = new Customer();
        mockUser.setName("test@example.com");
        mockUser.setPassword("encodedPassword");
        mockUser.setName("David");
        mockUser.setPhone("123456789");

        // Simulamos que el repositorio guarda el usuario correctamente
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Llamamos al método registerUser y verificamos que todo funcione bien
        User registered = userService.registerUser(dto);

        assertNotNull(registered);
        assertEquals("test@example.com", registered.getName());
        assertEquals("encodedPassword", registered.getPassword());
        assertEquals("David", registered.getName());
        assertEquals("123456789", registered.getPhone());
    }

    @Test
    void testFindByUsername() {
        // Creamos un objeto User (Customer) para ser retornado por el repositorio
        User user = new Customer();
        user.setName("test");

        // Simulamos que el repositorio encuentra al usuario con el correo proporcionado
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));

        // Llamamos al método findByUsername y verificamos que el usuario se encontró correctamente
        Optional<User> found = userService.findByUsername("test");

        assertTrue(found.isPresent());
        assertEquals("test", found.get().getName());
    }

    @Test
    void testRegisterUser_UsernameAlreadyExists() {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("1234");
        dto.setName("David");
        dto.setPhone("123456789");

        // Simulamos que el correo ya está registrado en la base de datos
        when(userRepository.existsByUsername("test@example.com")).thenReturn(true);

        // Verificamos que se lance una excepción cuando intentamos registrar un usuario con un correo ya existente
        Exception exception = assertThrows(RuntimeException.class, () -> userService.registerUser(dto));
        assertEquals("El email test@example.com ya está registrado", exception.getMessage());
    }
}
