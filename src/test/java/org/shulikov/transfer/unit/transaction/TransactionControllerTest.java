package org.shulikov.transfer.unit.transaction;

import static org.eclipse.jetty.http.HttpStatus.OK_200;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.shulikov.transfer.exception.BadRequestException;
import org.shulikov.transfer.exception.ResourceNotFoundException;
import org.shulikov.transfer.model.Transaction;

public class TransactionControllerTest extends TransactionControllerTestBase {

  @Test
  public void POST_to_perform_transaction_gives_200_for_validTransaction() {
    transactionController.perform(ctx);
    verify(ctx).status(OK_200);
  }

  @Test(expected = ResourceNotFoundException.class)
  public void POST_to_perform_transaction_throwsNotFount_for_nonExistedTransactionMember() {
    Transaction transaction = createTransaction();
    when(ctx.bodyAsClass(Transaction.class)).thenReturn(transaction);
    doThrow(new ResourceNotFoundException("Account with id 1 was not found"))
        .when(transactionService).perform(transaction);

    transactionController.perform(ctx);
  }

  @Test(expected = BadRequestException.class)
  public void POST_to_perform_transaction_throwsBadRequest_for_invalidBalanceFrom() {
    Transaction transaction = createTransaction();
    when(ctx.bodyAsClass(Transaction.class)).thenReturn(transaction);
    doThrow(new BadRequestException("Not enough money on the balance"))
        .when(transactionService).perform(transaction);

    transactionController.perform(ctx);
  }
}
