package com.n1.recommendationservice.dto;

/**
 * @author Mehmet Akif Tanisik
 */
public record RestaurantResponse(
        String id,
        String name,
        String location,
        Double averageScore
) {
}
