package com.n11.reviewservice.dto;

import jakarta.validation.constraints.*;

/**
 * @author Mehmet Akif Tanisik
 */
public record ReviewUpdateRequest(

        @NotNull
        Long id,

        @Min(value = 1, message = "Rate must be at least 1")
        @Max(value = 5, message = "Rate must be at most 5")
        byte rate,

        @NotBlank(message = "Comment cant be null or blank!")
        @Size(max = 150)
        String comment
) {
}
