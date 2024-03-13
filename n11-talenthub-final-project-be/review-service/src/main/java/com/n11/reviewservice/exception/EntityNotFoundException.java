package com.n11.reviewservice.exception;

/**
 * @author Mehmet Akif Tanisik
 */
public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message) {
        super(message);
    }
}
