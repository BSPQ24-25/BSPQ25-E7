package com.restaurant.reservation.dto;

/**
 * @class ReviewResponseDTO
 * @brief DTO de salida que representa una reseña realizada por un cliente.
 */
public class ReviewResponseDTO {

    private Long reviewId;
    private Long reservationId;
    private int rating;
    private String comment;

    /**
     * @brief Constructor de la reseña.
     * @param reviewId ID de la reseña.
     * @param reservationId ID de la reserva asociada.
     * @param rating Calificación (1-5).
     * @param comment Comentario textual.
     */

    public ReviewResponseDTO(Long reviewId, Long reservationId, int rating, String comment) {
        this.reviewId = reviewId;
        this.reservationId = reservationId;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters
    public Long getReviewId() {
        return reviewId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}