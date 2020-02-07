package org.shulikov.transfer.exception;

public class BadRequestException extends RuntimeException {

  public BadRequestException(String errorMessage) {
    super(errorMessage);
  }
}