package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para la entidad {@link User}.
 * Proporciona métodos para acceder a usuarios por su email.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por su email.
     *
     * @param email Email del usuario.
     * @return Un {@link Optional} con el usuario si existe.
     */
    Optional<User> findByEmail(String email);
}
