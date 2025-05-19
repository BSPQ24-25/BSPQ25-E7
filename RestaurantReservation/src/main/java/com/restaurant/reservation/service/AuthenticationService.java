package com.restaurant.reservation.service;

import com.restaurant.reservation.dto.LoginRequestDTO;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.repository.UserRepository;
import com.restaurant.reservation.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service responsible for user authentication and token generation.
 */
@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Authenticates a user based on email and password.
     * If the credentials are valid, a JWT token is generated and returned.
     *
     * @param loginRequestDTO DTO containing login credentials.
     * @return A JWT token if authentication is successful.
     * @throws RuntimeException if the email or password is incorrect.
     */
    public String authenticate(LoginRequestDTO loginRequestDTO) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequestDTO.getEmail());

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Email o contraseña incorrectos");
        }

        User user = userOptional.get();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Email o contraseña incorrectos");
        }

        return jwtTokenProvider.generateToken(user);
    }
}
