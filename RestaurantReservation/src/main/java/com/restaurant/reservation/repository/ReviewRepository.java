package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.reservation.reservationId = :reservationId")
    Review findByReservationId(@Param("reservationId") Long reservationId);

    @Query("SELECT r FROM Review r WHERE r.customer.id = :customerId")
    List<Review> findByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT r FROM Review r WHERE r.reservation.table.restaurant.id = :restaurantId")
    List<Review> findByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query("SELECT COUNT(r) > 0 FROM Review r WHERE r.reservation.reservationId = :reservationId")
    boolean existsByReservationId(@Param("reservationId") Long reservationId);
}