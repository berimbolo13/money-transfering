package org.shulikov.transfer.controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiRequestBody;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;
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

  @OpenApi(
      path = "/api/transactions",
      method = HttpMethod.POST,
      summary = "Transfer money between accounts",
      operationId = "performTransaction",
      tags = {"Transaction"},
      requestBody = @OpenApiRequestBody(
          description = "Transaction body",
          required = true,
          content = {@OpenApiContent(from = Transaction.class)}),
      responses = {
          @OpenApiResponse(
              status = "200",
              description = "Successfully transferred"),
          @OpenApiResponse(
              status = "400",
              description = "Not enough money on the balance FROM"),
          @OpenApiResponse(
              status = "400",
              description = "Duplicate accounts FROM and TO"),
          @OpenApiResponse(
              status = "400",
              description = "Amount in transaction less then one"),
          @OpenApiResponse(
              status = "404",
              description = "Account not found")
      }
  )
  public void perform(@NotNull Context context) {
    Transaction incomingTransaction = context.bodyAsClass(Transaction.class);
    transactionService.perform(incomingTransaction);
    context.status(HttpStatus.OK_200);
  }
}
