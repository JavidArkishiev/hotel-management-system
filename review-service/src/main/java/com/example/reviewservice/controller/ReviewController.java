package com.example.reviewservice.controller;

import com.example.reviewservice.model.dto.request.ReviewRequestDto;
import com.example.reviewservice.model.dto.responce.ReviewResponse;
import com.example.reviewservice.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<String> createReview(@RequestBody @Valid ReviewRequestDto requestDto,
                                               @RequestParam Long customerId,
                                               @RequestParam Long reservationId) {
        reviewService.createNewReview(requestDto, customerId, reservationId);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("any-review")
    public ResponseEntity<List<ReviewResponse>> getAnyReview(@RequestParam Long customerId,
                                                             @RequestParam Long reservationId) {

        return new ResponseEntity<>(reviewService.getAnyReview(customerId, reservationId), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ReviewResponse>> getAllReview(@RequestParam Long customerId) {
        return new ResponseEntity<>(reviewService.getAllReview(customerId), HttpStatus.OK);
    }

    @GetMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public ReviewResponse getReviewById(@PathVariable Long reviewId) {
        return reviewService.getReviewById(reviewId);

    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReviewById(@PathVariable Long reviewId,
                                                   @RequestBody @Valid ReviewRequestDto requestDto) {
        reviewService.updateReviewById(reviewId, requestDto);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteReviewById(@PathVariable Long reviewId) {
        reviewService.deleteReviewById(reviewId);
        return "Success";
    }
}
