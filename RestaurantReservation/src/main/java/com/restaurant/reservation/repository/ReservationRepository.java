package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio para la entidad {@link Reservation}.
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Busca las reservas de un usuario.
     *
     * @param user Usuario.
     * @return Lista de reservas.
     */
    List<Reservation> findByUser(User user);

    /**
     * Obtiene todas las reservas con datos de usuario incluidos.
     *
     * @return Lista de reservas con usuario.
     */
    @Query("SELECT r FROM Reservation r JOIN FETCH r.user")
    List<Reservation> findAllWithUser();

    /**
     * Cuenta las reservas en una fecha y hora específica.
     *
     * @param date Fecha.
     * @param hour Hora.
     * @return Número de reservas.
     */
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.date = :date AND r.hour = :hour")
    long countByDateAndHour(@Param("date") LocalDate date, @Param("hour") LocalTime hour);
}