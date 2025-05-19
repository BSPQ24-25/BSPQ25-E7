package com.restaurant.reservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a review made by a customer for a reservation.
 */
@Entity
@Table(name = "review")
public class Review implements Serializable {

    /**
     * Unique identifier for the review.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    /**
     * The customer who made the review.
     */
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    /**
     * The reservation being reviewed.
     */
    @OneToOne
    @JoinColumn(name = "reservation_id", nullable = false, unique = true)
    private Reservation reservation;

    /**
     * Rating given in the review (1 to 5).
     */
    @Min(1)
    @Max(5)
    @Column(name = "rating", nullable = false)
    private int rating;

    /**
     * Optional comment from the customer.
     */
    @Size(max = 255)
    @Column(name = "comment", length = 255)
    private String comment;

    /**
     * Timestamp when the review was created.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructors
    public Review() {}

    public Review(User customer, Reservation reservation, int rating, String comment) {
        this.customer = customer;
        this.reservation = reservation;
        this.rating = rating;
        this.comment = comment != null ? comment.trim() : null;
    }

    // Getters and Setters
    public Long getReviewId() {
        return reviewId;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = Math.max(1, Math.min(5, rating)); // Enforce range 1-5
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment != null ? comment.trim() : null;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Review{" +
               "reviewId=" + reviewId +
               ", customer=" + (customer != null ? customer.getId() : null) +
               ", reservation=" + (reservation != null ? reservation.getId() : null) +
               ", rating=" + rating +
               ", comment='" + comment + '\'' +
               ", createdAt=" + createdAt +
               '}';
    }
}