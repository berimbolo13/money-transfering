package org.shulikov.transfer.integration.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.NOT_FOUND_404;
import static org.eclipse.jetty.http.HttpStatus.OK_200;

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
    assertThat(getAccountBalance(from.getId())).isEqualTo(from.getBalance() - amount);
    assertThat(getAccountBalance(to.getId())).isEqualTo(to.getBalance() + amount);
  }

  @Test
  public void perform_invalid_transaction_duplicatesAccounts_returns400() {
    Account from = createAccount();
    Transaction transaction = new Transaction(from.getId(), from.getId(), 10);
    HttpResponse<String> response = performTransaction(transaction);

    assertThat(response.getStatus()).isEqualTo(BAD_REQUEST_400);
    assertThat(response.getBody()).isEqualTo(DUPLICATE_ACCOUNTS);
    assertThat(getAccountBalance(from.getId())).isEqualTo(from.getBalance());
  }

  @Test
  public void perform_invalid_transaction_negative_amount_returns400() {
    Account from = createAccount();
    Account to = createAccount();
    Transaction transaction = new Transaction(from.getId(), to.getId(), -10);
    HttpResponse<String> response = performTransaction(transaction);

    assertThat(response.getStatus()).isEqualTo(BAD_REQUEST_400);
    assertThat(response.getBody()).isEqualTo(INVALID_AMOUNT);
    assertThat(getAccountBalance(from.getId())).isEqualTo(from.getBalance());
    assertThat(getAccountBalance(to.getId())).isEqualTo(to.getBalance());
  }

  @Test
  public void perform_invalid_transaction_amount_zero_returns400() {
    Account from = createAccount();
    Account to = createAccount();
    Transaction transaction = new Transaction(from.getId(), to.getId(), 0);
    HttpResponse<String> response = performTransaction(transaction);

    assertThat(response.getStatus()).isEqualTo(BAD_REQUEST_400);
    assertThat(response.getBody()).isEqualTo(INVALID_AMOUNT);
    assertThat(getAccountBalance(from.getId())).isEqualTo(from.getBalance());
    assertThat(getAccountBalance(to.getId())).isEqualTo(to.getBalance());
  }

  @Test
  public void perform_valid_transaction_but_from_nonExisted_returns404() {
    Account to = createAccount();
    Transaction transaction = new Transaction(NON_EXISTED_ID, to.getId(), 10);
    HttpResponse<String> response = performTransaction(transaction);

    assertThat(response.getStatus()).isEqualTo(NOT_FOUND_404);
    assertThat(response.getBody()).isEqualTo(ACCOUNT_NOT_FOUND);
    assertThat(getAccountBalance(to.getId())).isEqualTo(to.getBalance());
  }

  @Test
  public void perform_valid_transaction_but_to_nonExisted_returns404() {
    Account from = createAccount();
    Transaction transaction = new Transaction(from.getId(), NON_EXISTED_ID, 10);
    HttpResponse<String> response = performTransaction(transaction);

    assertThat(response.getStatus()).isEqualTo(NOT_FOUND_404);
    assertThat(response.getBody()).isEqualTo(ACCOUNT_NOT_FOUND);
    assertThat(getAccountBalance(from.getId())).isEqualTo(from.getBalance());
  }

  @Test
  public void perform_valid_transaction_lowFromBalance_returns400() {
    Account from = createAccount();
    Account to = createAccount();
    Transaction transaction = new Transaction(from.getId(), to.getId(), Integer.MAX_VALUE);
    HttpResponse<String> response = performTransaction(transaction);

    assertThat(response.getStatus()).isEqualTo(BAD_REQUEST_400);
    assertThat(response.getBody()).isEqualTo(INVALID_BALANCE);
    assertThat(getAccountBalance(from.getId())).isEqualTo(from.getBalance());
    assertThat(getAccountBalance(to.getId())).isEqualTo(to.getBalance());
  }
}