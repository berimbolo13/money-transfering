package org.shulikov.transfer.integration.transaction;

import static kong.unirest.Unirest.get;
import static kong.unirest.Unirest.post;

import io.javalin.plugin.json.JavalinJson;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import kong.unirest.HttpResponse;
import org.shulikov.transfer.integration.IntegrationTestBase;
import org.shulikov.transfer.model.Account;
import org.shulikov.transfer.model.Transaction;

public abstract class TransactionIntegrationTestBase extends IntegrationTestBase {

  protected final String DUPLICATE_ACCOUNTS = "\"Accounts from and to must be different\"";

  protected final String INVALID_AMOUNT = "\"Amount for transaction must be greater than 0\"";

  protected final String INVALID_BALANCE = "\"Not enough money on the balance\"";

  protected HttpResponse<String> performTransaction(Transaction transaction) {
    return post(API_TRANSACTIONS).body(JavalinJson.toJson(transaction)).asString();
  }

  protected CompletableFuture<HttpResponse<String>> performTransactionAsync(Transaction transaction) {
    return post(API_TRANSACTIONS).body(JavalinJson.toJson(transaction)).asStringAsync();
  }

  protected AtomicInteger getAccountBalance(Long id) {
    return get(buildAccountUri(id)).asObject(Account.class).getBody().getBalance();
  }
}
