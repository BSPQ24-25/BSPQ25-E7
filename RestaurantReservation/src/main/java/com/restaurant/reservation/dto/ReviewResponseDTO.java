package com.restaurant.reservation.dto;

public class ReviewResponseDTO {

    private Long reviewId;
    private Long reservationId;
    private int rating;
    private String comment;

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