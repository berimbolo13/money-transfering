package org.shulikov.transfer.controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import org.shulikov.transfer.model.Transaction;
import org.shulikov.transfer.service.api.TransactionService;

@Singleton
public class TransactionController {

  private TransactionService transactionService;

  @Inject
  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  public void perform(@NotNull Context context) {
    Transaction incomingTransaction = context.bodyAsClass(Transaction.class);
    transactionService.perform(incomingTransaction);
    context.status(HttpStatus.OK_200);
  }
}
