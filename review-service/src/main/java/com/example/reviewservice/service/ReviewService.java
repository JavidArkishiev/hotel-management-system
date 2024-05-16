package com.example.reviewservice.service;

import com.example.reviewservice.client.CustomerServiceClient;
import com.example.reviewservice.client.ReservationServiceClient;
import com.example.reviewservice.exception.ReviewException;
import com.example.reviewservice.model.dto.request.ReviewRequestDto;
import com.example.reviewservice.model.dto.responce.CustomerResponseDto;
import com.example.reviewservice.model.dto.responce.ReservationResponseDto;
import com.example.reviewservice.model.dto.responce.ReviewResponse;
import com.example.reviewservice.model.entity.Review;
import com.example.reviewservice.model.mapper.ReviewMapper;
import com.example.reviewservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final CustomerServiceClient customerServiceClient;
    private final ReservationServiceClient reservationServiceClient;

    public void createNewReview(ReviewRequestDto requestDto, Long customerId, Long reservationId) {
        CustomerResponseDto responseDto = customerServiceClient.getCustomerById(customerId).getBody();
        ReservationResponseDto reservationResponseDto = reservationServiceClient.getReservationById(reservationId).getBody();
        if (responseDto != null && reservationResponseDto != null) {

            Review reviewEntity = reviewMapper.mapToReviewEntity(requestDto, customerId, reservationId);
            reviewRepository.save(reviewEntity);

        } else
            throw new ReviewException("customer or reservation is null");
    }


    public List<ReviewResponse> getAnyReview(Long customerId, Long reservationId) {
        List<Review> reviewList = reviewRepository.findByCustomerIdAndReservationId(customerId, reservationId);
        if (reviewList.isEmpty()) {
            throw new ReviewException("review is empty");
        }
        return reviewMapper.mapToReviewListDto(reviewList);
    }

    public List<ReviewResponse> getAllReview(Long customerId) {
        List<Review> reviewList = reviewRepository.findByCustomerId(customerId);
        return reviewMapper.mapToReviewListDto(reviewList);
    }

    public ReviewResponse getReviewById(Long reviewId) {
        Review reviewEntity = reviewRepository.findById(reviewId).
                orElseThrow(() -> new ReviewException("can not found review with this reviewId"));
        return reviewMapper.mapToReviewDto(reviewEntity);
    }

    public void updateReviewById(Long reviewId, ReviewRequestDto requestDto) {

        Review oldReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException("can not found review with this reviewId"));
        if (oldReview != null) {
            Review newReview = reviewMapper.mapToUpdateReviewEntity(requestDto, oldReview);
            newReview.setId(oldReview.getId());
            reviewRepository.save(newReview);
        }

    }
    public void deleteReviewById(Long reviewId) {
        Review reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException("can not found review with this reviewId"));
        reviewRepository.delete(reviewEntity);

    }
}
