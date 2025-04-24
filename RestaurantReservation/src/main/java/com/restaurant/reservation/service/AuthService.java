package com.restaurant.reservation.service;

import com.restaurant.reservation.dto.RegisterRequestDTO;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(RegisterRequestDTO dto) {
        User user = new User(
                dto.getEmail(),
                dto.getUsername(),
                dto.getPhone(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getUserType()
        );
        userRepository.save(user);
    }
}
