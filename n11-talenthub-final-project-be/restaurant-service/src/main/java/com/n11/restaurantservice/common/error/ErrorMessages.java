package com.n11.restaurantservice.common.error;

/**
 * @author Mehmet Akif Tanisik
 */
public enum ErrorMessages implements ErrorMessage {

  RESTAURANT_NOT_FOUND("restaurant.not.found"),
  NEGATIVE_AVERAGE_SCORE_EXCEPTION("negative.average.score"),
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
