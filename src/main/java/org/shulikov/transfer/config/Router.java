package org.shulikov.transfer.config;

import static io.javalin.apibuilder.ApiBuilder.crud;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.javalin.Javalin;
import org.shulikov.transfer.controller.AccountController;
import org.shulikov.transfer.controller.TransactionController;

@Singleton
public class Router {

  private AccountController accountController;

  private TransactionController transactionController;

  @Inject
  public Router(AccountController accountController, TransactionController transactionController) {
    this.accountController = accountController;
    this.transactionController = transactionController;
  }

  public void bindRoutes(Javalin app) {
    app.routes(() -> {
      crud("/api/accounts/:id", accountController);
      path("/api/transactions", () -> post(transactionController::perform));
    });
  }
}
