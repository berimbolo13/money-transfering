package org.shulikov.transfer.integration.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.NOT_FOUND_404;
import static org.eclipse.jetty.http.HttpStatus.OK_200;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import kong.unirest.HttpResponse;
import org.junit.Test;
import org.shulikov.transfer.model.Account;
import org.shulikov.transfer.model.Transaction;

public class TransactionIntegrationTest extends TransactionIntegrationTestBase {

  @Test
  public void perform_valid_transaction_returns200_balancesAreChanged() {
    Account from = createAccount();
    Account to = createAccount();
    int amount = 10;
    Transaction transaction = new Transaction(from.getId(), to.getId(), amount);
    HttpResponse<String> response = performTransaction(transaction);

    assertThat(response.getStatus()).isEqualTo(OK_200);
    assertThat(getAccountBalance(from.getId()).get()).isEqualTo(from.getBalance().get() - amount);
    assertThat(getAccountBalance(to.getId()).get()).isEqualTo(to.getBalance().get() + amount);
  }

  @Test
  public void perform_invalid_transaction_duplicatesAccounts_returns400() {
    Account from = createAccount();
    Transaction transaction = new Transaction(from.getId(), from.getId(), 10);
    HttpResponse<String> response = performTransaction(transaction);

    assertThat(response.getStatus()).isEqualTo(BAD_REQUEST_400);
    assertThat(response.getBody()).isEqualTo(DUPLICATE_ACCOUNTS);
    assertThat(getAccountBalance(from.getId()).get()).isEqualTo(from.getBalance().get());
  }

  @Test
  public void perform_invalid_transaction_negative_amount_returns400() {
    Account from = createAccount();
    Account to = createAccount();
    Transaction transaction = new Transaction(from.getId(), to.getId(), -10);
    HttpResponse<String> response = performTransaction(transaction);

    assertThat(response.getStatus()).isEqualTo(BAD_REQUEST_400);
    assertThat(response.getBody()).isEqualTo(INVALID_AMOUNT);
    assertThat(getAccountBalance(from.getId()).get()).isEqualTo(from.getBalance().get());
    assertThat(getAccountBalance(to.getId()).get()).isEqualTo(to.getBalance().get());
  }

  @Test
  public void perform_invalid_transaction_amount_zero_returns400() {
    Account from = createAccount();
    Account to = createAccount();
    Transaction transaction = new Transaction(from.getId(), to.getId(), 0);
    HttpResponse<String> response = performTransaction(transaction);

    assertThat(response.getStatus()).isEqualTo(BAD_REQUEST_400);
    assertThat(response.getBody()).isEqualTo(INVALID_AMOUNT);
    assertThat(getAccountBalance(from.getId()).get()).isEqualTo(from.getBalance().get());
    assertThat(getAccountBalance(to.getId()).get()).isEqualTo(to.getBalance().get());
  }

  @Test
  public void perform_valid_transaction_but_from_nonExisted_returns404() {
    Account to = createAccount();
    Transaction transaction = new Transaction(NON_EXISTED_ID, to.getId(), 10);
    HttpResponse<String> response = performTransaction(transaction);

    assertThat(response.getStatus()).isEqualTo(NOT_FOUND_404);
    assertThat(response.getBody()).isEqualTo(ACCOUNT_NOT_FOUND);
    assertThat(getAccountBalance(to.getId()).get()).isEqualTo(to.getBalance().get());
  }

  @Test
  public void perform_valid_transaction_but_to_nonExisted_returns404() {
    Account from = createAccount();
    Transaction transaction = new Transaction(from.getId(), NON_EXISTED_ID, 10);
    HttpResponse<String> response = performTransaction(transaction);

    assertThat(response.getStatus()).isEqualTo(NOT_FOUND_404);
    assertThat(response.getBody()).isEqualTo(ACCOUNT_NOT_FOUND);
    assertThat(getAccountBalance(from.getId()).get()).isEqualTo(from.getBalance().get());
  }

  @Test
  public void perform_valid_transaction_lowFromBalance_returns400() {
    Account from = createAccount();
    Account to = createAccount();
    Transaction transaction = new Transaction(from.getId(), to.getId(), Integer.MAX_VALUE);
    HttpResponse<String> response = performTransaction(transaction);

    assertThat(response.getStatus()).isEqualTo(BAD_REQUEST_400);
    assertThat(response.getBody()).isEqualTo(INVALID_BALANCE);
    assertThat(getAccountBalance(from.getId()).get()).isEqualTo(from.getBalance().get());
    assertThat(getAccountBalance(to.getId()).get()).isEqualTo(to.getBalance().get());
  }

  /**
   * This test demonstrates locking for accounts while transactions:
   *
   * First - the only one transaction will be successful.
   *
   * Second - you can see a time of the test execution which is two times more than single
   * transaction duration despite of async (concurrent) requests
   * It means that a server thread for the second transaction waits the end of the first
   */
  @Test
  public void perform_two_valid_transaction_returns200_onlyForOne()
      throws ExecutionException, InterruptedException {
    Account from = createAccount();
    Account to = createAccount();
    int amount = 100;
    Transaction transaction = new Transaction(from.getId(), to.getId(), amount);

    CompletableFuture<HttpResponse<String>> first = performTransactionAsync(transaction);
    CompletableFuture<HttpResponse<String>> second = performTransactionAsync(transaction);

    assertThat(first.get().getStatus()).isNotEqualTo(second.get().getStatus());
  }

  @Test
  public void perform_two_mutual_valid_transactions_withoutDeadlock()
      throws ExecutionException, InterruptedException {

    Account from = createAccount();
    Account to = createAccount();
    int amount = 100;
    Transaction transaction = new Transaction(from.getId(), to.getId(), amount);
    Transaction transaction2 = new Transaction(to.getId(), from.getId(), amount);

    CompletableFuture<HttpResponse<String>> first = performTransactionAsync(transaction);
    CompletableFuture<HttpResponse<String>> second = performTransactionAsync(transaction2);

    assertThat(first.get().getStatus()).isEqualTo(second.get().getStatus());
  }
}