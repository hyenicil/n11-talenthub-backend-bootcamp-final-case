package com.n11.reviewservice.common.error;

/**
 * @author Mehmet Akif Tanisik
 */
public enum ErrorMessages implements ErrorMessage {

  USER_NOT_FOUND("user.not.found"),
  RESTAURANT_NOT_FOUND("restaurant.not.found"),
  REVIEW_NOT_FOUND("review.not.found"),
  METHOD_ARGUMENT_NOT_VALID_MESSAGE("method.arguments.not.valid");

  private final String message;

  ErrorMessages(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return message;
  }
}
