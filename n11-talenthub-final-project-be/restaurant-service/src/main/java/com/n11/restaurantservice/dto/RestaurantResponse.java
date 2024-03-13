package com.n11.restaurantservice.dto;

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
