package org.shulikov.transfer.controller;

import static java.lang.Long.parseLong;
import static org.eclipse.jetty.http.HttpStatus.CREATED_201;
import static org.eclipse.jetty.http.HttpStatus.NO_CONTENT_204;
import static org.eclipse.jetty.http.HttpStatus.OK_200;
import static org.shulikov.transfer.util.HttpRequestUtil.getRequiredQueryParam;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiParam;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;
import org.jetbrains.annotations.NotNull;
import org.shulikov.transfer.model.Account;
import org.shulikov.transfer.service.api.AccountService;

@Singleton
public class AccountController implements CrudHandler {

  private AccountService accountService;

  @Inject
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @Override
  @OpenApi(
      summary = "Create account with holder name",
      operationId = "createAccountWithHolderName",
      tags = {"Account"},
      queryParams = {
          @OpenApiParam(
              name = "holderName",
              required = true,
              description = "The holder's name for the new account")},
      responses = {
          @OpenApiResponse(
              status = "201",
              content = {@OpenApiContent(from = Account.class)},
              description = "Successfully created"),
          @OpenApiResponse(
              status = "400",
              description = "Holder name must be specified")
      }
  )
  public void create(@NotNull Context context) {
    context.json(accountService.create(getRequiredQueryParam(context, "holderName")));
    context.status(CREATED_201);
  }

  @OpenApi(
      summary = "Delete account",
      operationId = "deleteAccount",
      tags = {"Account"},
      pathParams = {
          @OpenApiParam(name = "id", type = Integer.class, description = "Account id")},
      responses = {
          @OpenApiResponse(
              status = "204",
              description = "Successfully deleted"),
          @OpenApiResponse(
              status = "404",
              description = "Account not found")
      }
  )
  @Override
  public void delete(@NotNull Context context, @NotNull String id) {
    accountService.delete(parseLong(id));
    context.status(NO_CONTENT_204);
  }

  @OpenApi(
      summary = "Get all account",
      operationId = "getAllAccounts",
      tags = {"Account"},
      responses = {
          @OpenApiResponse(
              status = "200",
              content = {@OpenApiContent(from = Account[].class)})
      }
  )
  @Override
  public void getAll(@NotNull Context context) {
    context.json(accountService.findAll());
    context.status(OK_200);
  }

  @Override
  @OpenApi(
      summary = "Get account",
      operationId = "getAccount",
      tags = {"Account"},
      pathParams = {
          @OpenApiParam(name = "id", type = Integer.class, description = "Account id")},
      responses = {
          @OpenApiResponse(
              status = "200",
              description = "Successfully gotten"),
          @OpenApiResponse(
              status = "404",
              description = "Account not found")
      }
  )
  public void getOne(@NotNull Context context, @NotNull String id) {
    context.json(accountService.getById(parseLong(id)));
    context.status(OK_200);
  }

  @OpenApi(
      summary = "Update account with holder name",
      operationId = "updateAccountWithHolderName",
      tags = {"Account"},
      queryParams = {
          @OpenApiParam(
              name = "holderName",
              required = true,
              description = "The new holder's name for the account")},
      responses = {
          @OpenApiResponse(
              status = "200",
              content = {@OpenApiContent(from = Account.class)},
              description = "Successfully updated"),
          @OpenApiResponse(
              status = "400",
              description = "Holder name must be specified"),
          @OpenApiResponse(
              status = "404",
              description = "Account not found")
      }
  )
  @Override
  public void update(@NotNull Context context, @NotNull String id) {
    String newHolderName = getRequiredQueryParam(context, "holderName");
    context.json(accountService.update(parseLong(id), newHolderName));
    context.status(OK_200);
  }
}
