package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for accessing User entities from the database.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by email.
     * @param email The email of the user.
     * @return An optional containing the found user, or empty if not found.
     */
    Optional<User> findByEmail(String email);
}

