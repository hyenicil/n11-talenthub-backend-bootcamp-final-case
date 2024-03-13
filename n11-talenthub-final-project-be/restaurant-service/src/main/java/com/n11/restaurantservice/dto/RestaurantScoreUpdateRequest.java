package com.n11.restaurantservice.dto;

/**
 * @author Mehmet Akif Tanisik
 */
public record RestaurantScoreUpdateRequest (
        Double averageScore,
        String restaurantId
){
}
