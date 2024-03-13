package com.n11.reviewservice.exception;

import com.n11.reviewservice.common.error.ErrorMessages;

/**
 * @author Mehmet Akif Tanisik
 */
public class RestaurantNotFoundException extends EntityNotFoundException {
    public RestaurantNotFoundException(ErrorMessages message) {
        super(message.getMessage());
    }
}
