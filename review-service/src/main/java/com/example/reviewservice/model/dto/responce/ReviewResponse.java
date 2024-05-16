package com.example.reviewservice.model.dto.responce;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponse {
    private Long customerId;

    private Long reservationId;

    private String comment;

    private int rating;

    private LocalDateTime creationDate;
}
