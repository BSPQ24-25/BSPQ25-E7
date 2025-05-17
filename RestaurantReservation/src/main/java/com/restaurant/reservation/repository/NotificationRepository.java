package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findBySeenFalse();

    @Modifying
    @Query("UPDATE Notification n SET n.seen = true WHERE n.seen = false")
    void markAllAsSeen();
}
