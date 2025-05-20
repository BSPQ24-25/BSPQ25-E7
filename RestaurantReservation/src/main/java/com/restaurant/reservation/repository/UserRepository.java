package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
<<<<<<< HEAD
 * Repository interface for accessing User entities from the database.
=======
 * Repositorio para la entidad {@link User}.
 * Proporciona métodos para acceder a usuarios por su email.
>>>>>>> 2fa7554a80bfbc37e8cdf443ca991ed3b9ef4758
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
<<<<<<< HEAD
     * Finds a user by email.
     * @param email The email of the user.
     * @return An optional containing the found user, or empty if not found.
=======
     * Busca un usuario por su email.
     *
     * @param email Email del usuario.
     * @return Un {@link Optional} con el usuario si existe.
>>>>>>> 2fa7554a80bfbc37e8cdf443ca991ed3b9ef4758
     */
    Optional<User> findByEmail(String email);
}

