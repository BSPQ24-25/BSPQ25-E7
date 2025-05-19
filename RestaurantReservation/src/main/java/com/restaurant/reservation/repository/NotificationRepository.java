package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repositorio para {@link Notification}.
 * Permite marcar notificaciones como vistas.
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Encuentra todas las notificaciones no vistas.
     *
     * @return Lista de notificaciones no vistas.
     */
    List<Notification> findBySeenFalse();

    /**
     * Marca todas las notificaciones como vistas.
     */
    @Modifying
    @Query("UPDATE Notification n SET n.seen = true WHERE n.seen = false")
    void markAllAsSeen();
}