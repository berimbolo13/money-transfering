package org.shulikov.transfer.unit.transaction.service;

import static org.mockito.Mockito.doThrow;

import org.junit.Test;
import org.shulikov.transfer.exception.BadRequestException;
import org.shulikov.transfer.model.Transaction;

public class TransactionServiceTest extends TransactionServiceTestBase {

  @Test
  public void perform_transaction_which_isValid() {
    Transaction transaction = createTransaction();
    transactionService.perform(transaction);
  }

  @Test(expected = BadRequestException.class)
  public void perform_transaction_duplicate_accounts_throws_badRequest() {
    Transaction transaction = new Transaction(1L, 1L, 1);
    doThrow(new BadRequestException("Accounts from and to must be different"))
        .when(transactionValidator).validateTransaction(transaction);

    transactionService.perform(transaction);
  }

  @Test(expected = BadRequestException.class)
  public void perform_transaction_invalid_amount_throws_badRequest() {
    Transaction transaction = new Transaction(1L, 2L, -1);
    doThrow(new BadRequestException("Amount for transaction must be greater than 0"))
        .when(transactionValidator).validateTransaction(transaction);

    transactionService.perform(transaction);
  }
}
