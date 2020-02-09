package org.shulikov.transfer.validator.impl;

import org.shulikov.transfer.exception.BadRequestException;
import org.shulikov.transfer.model.Transaction;
import org.shulikov.transfer.validator.api.TransactionValidator;

public class TransactionValidatorImpl implements TransactionValidator {


  @Override
  public void validateTransaction(Transaction transaction) {
    if (transaction.getFrom().equals(transaction.getTo())) {
      throw new BadRequestException("Accounts from and to must be different");
    }
    if (transaction.getAmount() <= 0) {
      throw new BadRequestException("Amount for transaction must be greater than 0");
    }
  }
}
