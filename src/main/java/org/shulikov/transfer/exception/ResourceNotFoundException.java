package org.shulikov.transfer.exception;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}