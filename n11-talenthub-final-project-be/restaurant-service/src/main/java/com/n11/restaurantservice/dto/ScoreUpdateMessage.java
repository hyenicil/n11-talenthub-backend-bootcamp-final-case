package com.n11.restaurantservice.dto;

/**
 * @author Mehmet Akif Tanisik
 */
public record ScoreUpdateMessage(
        double averageScore,
        String restaurantId
) {
}
