package com.n11.reviewservice.service;

import com.n11.reviewservice.dto.ReviewResponse;
import com.n11.reviewservice.dto.ReviewSaveRequest;
import com.n11.reviewservice.dto.ReviewUpdateRequest;

import java.io.IOException;
import java.util.List;

/**
 * @author Mehmet Akif Tanisik
 */
public interface ReviewService {
    ReviewResponse save(ReviewSaveRequest request) throws IOException;
    ReviewResponse getById(Long id);
    List<ReviewResponse> getAll();
    ReviewResponse update(Long id, ReviewUpdateRequest request);
    void delete(Long id);
    List<ReviewResponse> getAllByUserId(Long userId);
    List<ReviewResponse> getAllByRestaurantId(String restaurantId);
}
