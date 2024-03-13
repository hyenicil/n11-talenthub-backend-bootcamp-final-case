package com.n11.restaurantservice.exception;

import com.n11.restaurantservice.common.error.ErrorMessages;

/**
 * @author Mehmet Akif Tanisik
 */
public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(ErrorMessages message) {
        super(message.getMessage());
    }
}
