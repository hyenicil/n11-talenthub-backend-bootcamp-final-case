package com.n11.userservice.exception;

/**
 * @author Mehmet Akif Tanisik
 */
public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message) {
        super(message);
    }
}
