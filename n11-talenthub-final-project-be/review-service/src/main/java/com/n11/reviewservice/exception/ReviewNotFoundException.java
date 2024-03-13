package com.n11.reviewservice.exception;

import com.n11.reviewservice.common.error.ErrorMessages;

/**
 * @author Mehmet Akif Tanisik
 */
public class ReviewNotFoundException extends EntityNotFoundException{
    public ReviewNotFoundException(ErrorMessages message) {
        super(message.getMessage());
    }
}
