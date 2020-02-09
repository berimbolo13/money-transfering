package org.shulikov.transfer.validator.api;

import org.shulikov.transfer.model.Transaction;

public interface TransactionValidator {

  void validateTransaction(Transaction transaction);
}
