package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Repositorio para {@link RestaurantTable}.
 * Contiene lógica para encontrar mesas disponibles.
 */
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Integer> {

  /**
   * Busca mesas disponibles según la fecha, hora y cantidad de personas.
   *
   * @param date Fecha de la reserva.
   * @param hour Hora de la reserva.
   * @param nPeople Cantidad de personas.
   * @return Lista de mesas disponibles.
   */
  @Query("""
      SELECT t FROM RestaurantTable t
      WHERE t.capacity >= :nPeople
        AND t.state = 'available'
        AND NOT EXISTS (
          SELECT r FROM Reservation r
          WHERE r.table = t
            AND r.date = :date
            AND r.hour = :hour
      )
      """)
  List<RestaurantTable> findAvailableTables(
      @Param("date") LocalDate date,
      @Param("hour") LocalTime hour,
      @Param("nPeople") int nPeople
  );
}