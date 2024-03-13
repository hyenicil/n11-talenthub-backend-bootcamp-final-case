package com.n1.recommendationservice.exception;

import com.n1.recommendationservice.common.error.ErrorMessages;

/**
 * @author Mehmet Akif Tanisik
 */
public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException(ErrorMessages message) {
        super(message.getMessage());
    }
}
