package com.n11.restaurantservice.exception;

import com.n11.restaurantservice.common.error.ErrorMessages;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Mehmet Akif Tanisik
 */
@Slf4j
public class ScoreUpdaterQueueException extends RuntimeException {
    public ScoreUpdaterQueueException(ErrorMessages message) {
        super(message.getMessage());
        log.info(message.getMessage());
    }
}
