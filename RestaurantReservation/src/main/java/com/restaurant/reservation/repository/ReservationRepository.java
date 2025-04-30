package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(User user);

    @Query("SELECT r FROM Reservation r JOIN FETCH r.user")
    List<Reservation> findAllWithUser();

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.date = :date AND r.hour = :hour")
    long countByDateAndHour(@Param("date") LocalDate date, @Param("hour") LocalTime hour);
}
