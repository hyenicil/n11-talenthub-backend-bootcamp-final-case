package com.n1.recommendationservice.exception.handler;


import com.n1.recommendationservice.common.base.BaseRestResponse;
import com.n1.recommendationservice.common.error.ErrorResponse;
import com.n1.recommendationservice.exception.SolrIOException;
import com.n1.recommendationservice.exception.SolrServerAccessException;
import com.n1.recommendationservice.exception.AddressNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
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
import java.util.Set;

import static com.n1.recommendationservice.common.error.ErrorMessages.UNEXPECTED_ERROR_OCCURRED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;


/**
 * @author Mehmet Akif Tanisik
 */
@ControllerAdvice
public class GeneralErrorHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(WebRequest request) {

        String description = request.getDescription(false);

        var generalErrorMessages = new ErrorResponse(LocalDateTime.now(), UNEXPECTED_ERROR_OCCURRED.getMessage(), new HashMap<>(), description);

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

        var generalErrorMessages = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), errorMap,  description);
        var restResponse = BaseRestResponse.error(generalErrorMessages);

        return new ResponseEntity<>(restResponse, BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {

        String description = request.getDescription(false);

        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        Map<String, String> errorMap = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            String propertyPath = getLastNode(violation.getPropertyPath().toString());
            String message = violation.getMessage();
            errorMap.put(propertyPath, message);
        }

        var generalErrorMessages = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), errorMap, description);
        var restResponse = BaseRestResponse.error(generalErrorMessages);

        return new ResponseEntity<>(restResponse, BAD_REQUEST);
    }


    @ExceptionHandler(SolrServerAccessException.class)
    protected ResponseEntity<Object> handleSolrServerAccessException(SolrServerAccessException ex, WebRequest request) {

        String description = request.getDescription(false);

        var generalErrorMessages = new ErrorResponse(LocalDateTime.now(), ex.getMessage() , new HashMap<>(), description);

        var restResponse = BaseRestResponse.error(generalErrorMessages);

        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    protected ResponseEntity<Object> handleUserResponseNullException(AddressNotFoundException ex, WebRequest request) {

        String description = request.getDescription(false);

        var generalErrorMessages = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), new HashMap<>(), description);

        var restResponse = BaseRestResponse.error(generalErrorMessages);

        return new ResponseEntity<>(restResponse, BAD_REQUEST);
    }

    @ExceptionHandler(SolrIOException.class)
    protected ResponseEntity<Object> handleSolrIOException(SolrIOException ex, WebRequest request) {

        String description = request.getDescription(false);

        var generalErrorMessages = new ErrorResponse(LocalDateTime.now(), ex.getMessage() , new HashMap<>(), description);

        var restResponse = BaseRestResponse.error(generalErrorMessages);

        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getLastNode(String propertyPath) {
        if (propertyPath==null) {
            return "";
        } else {
            String[] parts = propertyPath.split("\\.");

            return parts[parts.length - 1];
        }
    }
}
