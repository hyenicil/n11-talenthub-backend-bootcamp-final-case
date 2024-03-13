package com.n11.reviewservice.mapper;

import com.n11.reviewservice.dto.ReviewResponse;
import com.n11.reviewservice.dto.ReviewSaveRequest;
import com.n11.reviewservice.dto.ReviewUpdateRequest;
import com.n11.reviewservice.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @author Mehmet Akif Tanisik
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ReviewMapper {

    Review mapReviewSaveRequestToReview(ReviewSaveRequest request);

    ReviewResponse mapReviewToReviewResponse(Review review);

    List<ReviewResponse> mapReviewListToReviewResponseList(List<Review> reviewList);

    Review mapReviewUpdateRequestToReview(@MappingTarget Review review, ReviewUpdateRequest request);
}
