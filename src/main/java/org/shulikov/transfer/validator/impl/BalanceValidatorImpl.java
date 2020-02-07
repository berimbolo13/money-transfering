package org.shulikov.transfer.validator.impl;

import org.shulikov.transfer.exception.BadRequestException;
import org.shulikov.transfer.validator.api.BalanceValidator;

public class BalanceValidatorImpl implements BalanceValidator {

  @Override
  public void validateForTransaction(int balance, int amount) {
    if (balance < amount) {
      throw new BadRequestException("Invalid balance");
    }
  }
}
