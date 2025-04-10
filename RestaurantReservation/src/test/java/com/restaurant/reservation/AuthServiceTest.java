package com.restaurant.reservation;

import com.restaurant.reservation.dto.LoginRequestDTO;
import com.restaurant.reservation.dto.RegisterRequestDTO;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.model.UserType;
import com.restaurant.reservation.repository.UserRepository;
import com.restaurant.reservation.security.CustomUserDetails;
import com.restaurant.reservation.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private LoginRequestDTO loginDTO;
    private User mockUser;

    @BeforeEach
    public void setUp() {
        loginDTO = new LoginRequestDTO();
        loginDTO.setEmail("cliente2@email.com");
        loginDTO.setPassword("23456789B");

        mockUser = new User();
        mockUser.setEmail("cliente2@email.com");
        mockUser.setPassword("hashedPassword");
        mockUser.setUserType(UserType.CUSTOMER);
    }

    @Test
    public void loginSuccessTest_asCustomer_shouldRedirectToCustomerHome() {
        CustomUserDetails userDetails = new CustomUserDetails(mockUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);

        when(authenticationManager.authenticate(any())).thenReturn(auth);

        String result = authService.login(loginDTO);

        assertEquals("redirect:/customer/home", result);
    }

    @Test
    public void loginSuccessTest_asAdmin_shouldRedirectToAdminHome() {
        mockUser.setUserType(UserType.ADMIN);
        CustomUserDetails userDetails = new CustomUserDetails(mockUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);

        when(authenticationManager.authenticate(any())).thenReturn(auth);

        String result = authService.login(loginDTO);

        assertEquals("redirect:/admin/home", result);
    }

        @Test
    public void registerSuccessTest() {
        RegisterRequestDTO dto = new RegisterRequestDTO();
        dto.setEmail("nuevo@email.com");
        dto.setUsername("Nuevo Usuario");
        dto.setPhone("123456789");
        dto.setPassword("password123");
        dto.setUserType(UserType.CUSTOMER);

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPass");

        String result = authService.register(dto);

        verify(userRepository).save(any(User.class));
        assertEquals("redirect:/login?registered=true", result);
    }

    @Test
    public void registerExistingUserTest_shouldRedirectToError() {
        RegisterRequestDTO dto = new RegisterRequestDTO();
        dto.setEmail("existente@email.com");
        dto.setUsername("Usuario Existente");
        dto.setPhone("987654321");
        dto.setPassword("otraClave123");
        dto.setUserType(UserType.ADMIN);

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(new User()));

        String result = authService.register(dto);

        verify(userRepository, never()).save(any(User.class));
        assertEquals("redirect:/register?error=exists", result);
    }
}
