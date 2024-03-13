package com.n11.userservice.dto.address;

/**
 * @author Mehmet Akif Tanisik
 */
public record AddressResponse(
        Long id,
        String city,
        String district,
        String street,
        String location,
        Long userId
) {
}
