package com.restaurant.reservation.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a notification message for a user.
 */
@Entity
public class Notification {

    /**
     * Unique identifier for the notification.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The notification message content.
     */
    private String message;

    /**
     * Indicates whether the notification has been seen.
     */
    private boolean seen;

    /**
     * Timestamp when the notification was created.
     */
    private LocalDateTime createdAt;

    /**
     * Default constructor.
     */
    public Notification() {}

    /**
     * Constructor with message.
     * @param message the notification message
     */
    public Notification(String message) {
        this.message = message;
        this.seen = false;
        this.createdAt = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isSeen() { return seen; }
    public void setSeen(boolean seen) { this.seen = seen; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}