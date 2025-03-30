package com.restaurant.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurant.reservation.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}