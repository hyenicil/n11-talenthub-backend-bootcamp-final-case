package com.n11.restaurantservice.exception.handler;


import com.n11.restaurantservice.common.base.BaseRestResponse;
import com.n11.restaurantservice.common.error.ErrorResponse;
import com.n11.restaurantservice.exception.RestaurantNotFoundException;
import com.n11.restaurantservice.exception.ScoreUpdaterQueueException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.n11.restaurantservice.common.error.ErrorMessages.METHOD_ARGUMENT_NOT_VALID_MESSAGE;


/**
 * @author Mehmet Akif Tanisik
 */
@ControllerAdvice
@Slf4j
public class GeneralErrorHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex, WebRequest request) {

        String message = ex.getMessage();

        String description = request.getDescription(false);

        var generalErrorMessages = new ErrorResponse(LocalDateTime.now(), message, new HashMap<>(), description);

        var restResponse = BaseRestResponse.error(generalErrorMessages);

        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {

        String description = request.getDescription(false);

        List<FieldError> fieldErrors = ex.getFieldErrors();

        Map<String, String> errorMap = new HashMap<>();

        for (FieldError error: fieldErrors) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }

        var generalErrorMessages = new ErrorResponse(LocalDateTime.now(), METHOD_ARGUMENT_NOT_VALID_MESSAGE.getMessage(), errorMap,  description);
        var restResponse = BaseRestResponse.error(generalErrorMessages);

        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    protected ResponseEntity<Object> handleRestaurantNotFoundException(RestaurantNotFoundException ex, WebRequest request) {

        String message = ex.getMessage();

        String description = request.getDescription(false);

        var generalErrorMessages = new ErrorResponse(LocalDateTime.now(), message, new HashMap<>(), description);

        var restResponse = BaseRestResponse.error(generalErrorMessages);

        return new ResponseEntity<>(restResponse, HttpStatus.NOT_FOUND);
    }

}
