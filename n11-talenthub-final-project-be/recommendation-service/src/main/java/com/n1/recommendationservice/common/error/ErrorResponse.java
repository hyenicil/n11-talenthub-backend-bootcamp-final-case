package com.n1.recommendationservice.common.error;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Mehmet Akif Tanisik
 */
public record ErrorResponse(
        LocalDateTime date,
        String message,
        Map<String, String> details,
        String description
) {
}
