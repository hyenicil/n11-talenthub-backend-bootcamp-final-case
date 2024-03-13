package com.n11.reviewservice.dto;

/**
 * @author Mehmet Akif Tanisik
 */
public record ReviewResponse(
        Long id,
        Long userId,
        String restaurantId,
        byte rate,
        String comment
) {
}
