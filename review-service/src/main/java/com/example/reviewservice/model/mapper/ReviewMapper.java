package com.example.reviewservice.model.mapper;

import com.example.reviewservice.model.dto.request.ReviewRequestDto;
import com.example.reviewservice.model.dto.responce.ReviewResponse;
import com.example.reviewservice.model.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "creationDate", expression = "java(java.time.LocalDateTime.now())")
    Review mapToReviewEntity(ReviewRequestDto requestDto, Long customerId, Long reservationId);

    List<ReviewResponse> mapToReviewListDto(List<Review> reviewList);

    ReviewResponse mapToReviewDto(Review reviewEntity);

    @Mapping(target = "reservationId", ignore = true)
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "creationDate", expression = "java(java.time.LocalDateTime.now())")
    Review mapToUpdateReviewEntity(ReviewRequestDto requestDto, @MappingTarget Review oldReview);
}
