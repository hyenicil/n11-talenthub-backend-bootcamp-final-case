package com.n11.restaurantservice.dto;

import com.n11.restaurantservice.validation.ValidLocationPattern;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Mehmet Akif Tanisik
 */
public record RestaurantUpdateRequest(
        @NotBlank(message = "City cant be null or blank!")
        @Size(max = 60)
        String name,

        @ValidLocationPattern
        String location
) {
}
