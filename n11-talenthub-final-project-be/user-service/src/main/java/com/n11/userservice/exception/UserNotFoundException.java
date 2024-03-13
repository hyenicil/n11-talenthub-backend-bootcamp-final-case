package com.n11.userservice.exception;

import com.n11.userservice.common.error.ErrorMessages;

/**
 * @author Mehmet Akif Tanisik
 */
public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(ErrorMessages message) {
        super(message.getMessage());
    }
}
