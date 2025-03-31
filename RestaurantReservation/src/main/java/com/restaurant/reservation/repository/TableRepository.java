package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TableRepository extends JpaRepository<RestaurantTable, Long> {
    Optional<RestaurantTable> findById(Long id);
}
