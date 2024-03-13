package com.n1.recommendationservice.dto;

/**
 * @author Mehmet Akif Tanisik
 */
public record AddressResponse(
        String city,
        String district,
        String street,
        String location
) {
}
