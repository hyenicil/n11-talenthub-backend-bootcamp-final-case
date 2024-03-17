package com.n11.loggingservice.exception;

/**
 * @author Mehmet Akif Tanisik
 */
public class UnProcessableLogException extends RuntimeException{
    public UnProcessableLogException(String message) {
        super(message);
    }
}
