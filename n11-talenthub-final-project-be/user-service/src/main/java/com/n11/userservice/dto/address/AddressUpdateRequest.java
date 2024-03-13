package com.n11.userservice.dto.address;

import com.n11.userservice.validation.ValidLocationPattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author Mehmet Akif Tanisik
 */
public record AddressUpdateRequest(
        @NotBlank(message = "City cant be null or blank!")
        @Size(max = 60)
        String city,

        @NotBlank(message = "District cant be null or blank!")
        @Size(max = 60)
        String district,

        @NotBlank(message = "Street cant be null or blank!")
        @Size(max = 60)
        String street,

        @ValidLocationPattern
        String location
) {
}
