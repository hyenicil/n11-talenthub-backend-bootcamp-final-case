package com.n11.reviewservice.exception;

import com.n11.reviewservice.common.error.ErrorMessages;

/**
 * @author Mehmet Akif Tanisik
 */
public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(ErrorMessages message) {
        super(message.getMessage());
    }
}
