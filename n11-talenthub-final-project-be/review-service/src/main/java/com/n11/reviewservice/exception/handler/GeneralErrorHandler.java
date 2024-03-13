package com.n11.reviewservice.exception.handler;

import com.n11.reviewservice.common.base.BaseRestResponse;
import com.n11.reviewservice.common.error.ErrorResponse;
import com.n11.reviewservice.exception.EntityNotFoundException;
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

import static com.n11.reviewservice.common.error.ErrorMessages.METHOD_ARGUMENT_NOT_VALID_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author Mehmet Akif Tanisik
 */
@ControllerAdvice
public class GeneralErrorHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex, WebRequest request) {

        String message = ex.getMessage();

        String description = request.getDescription(false);

        var generalErrorMessages = new ErrorResponse(LocalDateTime.now(), message, new HashMap<>(), description);

        var restResponse = BaseRestResponse.error(generalErrorMessages);

        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {

        String message = ex.getMessage();

        String description = request.getDescription(false);

        var generalErrorMessages = new ErrorResponse(LocalDateTime.now(), message, new HashMap<>(), description);

        var restResponse = BaseRestResponse.error(generalErrorMessages);

        return new ResponseEntity<>(restResponse, HttpStatus.NOT_FOUND);
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

    private String getLastNode(String propertyPath) {
        if (propertyPath==null) {
            return "";
        } else {
            String[] parts = propertyPath.split("\\.");

            return parts[parts.length - 1];
        }
    }
}
