package com.n11.reviewservice.dto;

import lombok.Builder;

/**
 * @author Mehmet Akif Tanisik
 */
@Builder
public record ScoreUpdateMessage(
        double averageScore,
        String restaurantId
) {
}
