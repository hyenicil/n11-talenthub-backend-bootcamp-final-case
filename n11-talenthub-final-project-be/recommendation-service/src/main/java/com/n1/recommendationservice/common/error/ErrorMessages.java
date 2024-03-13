package com.n1.recommendationservice.common.error;

/**
 * @author Mehmet Akif Tanisik
 */
public enum ErrorMessages implements ErrorMessage {

  SOLR_SERVER_EXCEPTION_MESSAGE("solr.server.exception.occurred"),
  SOLR_IO_EXCEPTION_MESSAGE("solr.io.exception.occurred"),
  METHOD_ARGUMENT_NOT_VALID_MESSAGE("method.arguments.not.valid"),
  CONSTRAINT_VIOLATION_MESSAGE("constraint.violation.for.validation"),
  UNEXPECTED_ERROR_OCCURRED("unexpected.error.occurred"),
  ADDRESS_NOT_FOUND_EXCEPTION_MESSAGE("address.not.found");

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
