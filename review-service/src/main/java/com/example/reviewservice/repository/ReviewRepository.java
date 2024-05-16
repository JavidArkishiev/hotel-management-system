package com.example.reviewservice.repository;

import com.example.reviewservice.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByCustomerIdAndReservationId(Long customerId, Long reservationId);
    List<Review> findByCustomerId(Long customerId);
}
