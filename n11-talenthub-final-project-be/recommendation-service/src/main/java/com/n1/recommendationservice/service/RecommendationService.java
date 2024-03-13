package com.n1.recommendationservice.service;

import com.n1.recommendationservice.dto.RestaurantResponse;

import java.util.List;

/**
 * @author Mehmet Akif Tanisik
 */
public interface RecommendationService {

    List<RestaurantResponse> getRecommendedRestaurants(Long userId);
}
