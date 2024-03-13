package com.n11.userservice.exception;

import com.n11.userservice.common.error.ErrorMessages;

/**
 * @author Mehmet Akif Tanisik
 */
public class AddressNotFoundException extends EntityNotFoundException {
    public AddressNotFoundException(ErrorMessages message) {
        super(message.getMessage());
    }
}
