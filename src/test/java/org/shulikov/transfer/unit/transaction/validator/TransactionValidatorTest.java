package org.shulikov.transfer.unit.transaction.validator;

import org.junit.Test;
import org.shulikov.transfer.exception.BadRequestException;
import org.shulikov.transfer.model.Transaction;

public class TransactionValidatorTest extends TransactionValidatorTestBase {

  @Test
  public void validate_valid_transaction() {
    Transaction transaction = createTransaction();
    transactionValidator.validateTransaction(transaction);
  }

  @Test(expected = BadRequestException.class)
  public void validate_transaction_whichHas_duplicateAccounts() {
    Transaction transaction = new Transaction(1L, 1L, 5);
    transactionValidator.validateTransaction(transaction);
  }

  @Test(expected = BadRequestException.class)
  public void validate_transaction_whichHas_invalidAmountMinusOne() {
    Transaction transaction = new Transaction(1L, 1L, -1);
    transactionValidator.validateTransaction(transaction);
  }

  @Test(expected = BadRequestException.class)
  public void validate_transaction_whichHas_invalidAmountZero() {
    Transaction transaction = new Transaction(1L, 1L, 0);
    transactionValidator.validateTransaction(transaction);
  }
}
