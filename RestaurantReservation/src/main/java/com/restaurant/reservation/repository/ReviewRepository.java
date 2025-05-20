package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para la entidad {@link Review}.
 * Contiene consultas personalizadas relacionadas con reservas y clientes.
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Obtiene una reseña a partir del ID de la reserva.
     *
     * @param reservationId ID de la reserva.
     * @return La reseña asociada.
     */
    @Query("SELECT r FROM Review r WHERE r.reservation.reservationId = :reservationId")
    Review findByReservationId(@Param("reservationId") Long reservationId);

    /**
     * Obtiene todas las reseñas hechas por un cliente específico.
     *
     * @param customerId ID del cliente.
     * @return Lista de reseñas.
     */
    @Query("SELECT r FROM Review r WHERE r.customer.id = :customerId")
    List<Review> findByCustomerId(@Param("customerId") Long customerId);

    /**
     * Obtiene todas las reseñas para un restaurante específico.
     *
     * @param restaurantId ID del restaurante.
     * @return Lista de reseñas.
     */
    @Query("SELECT r FROM Review r WHERE r.reservation.table.restaurant.id = :restaurantId")
    List<Review> findByRestaurantId(@Param("restaurantId") Long restaurantId);

    /**
     * Verifica si ya existe una reseña para una reserva.
     *
     * @param reservationId ID de la reserva.
     * @return true si ya existe reseña.
     */
    @Query("SELECT COUNT(r) > 0 FROM Review r WHERE r.reservation.reservationId = :reservationId")
    boolean existsByReservationId(@Param("reservationId") Long reservationId);
}