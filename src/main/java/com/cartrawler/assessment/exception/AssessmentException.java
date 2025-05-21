package com.cartrawler.assessment.exception;

public class AssessmentException extends RuntimeException {

  public AssessmentException(String message, Throwable cause) {
    super(message, cause);
  }

  public AssessmentException(String message) {
    super(message);
  }
}
