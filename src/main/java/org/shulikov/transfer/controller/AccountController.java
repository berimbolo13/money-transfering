package org.shulikov.transfer.controller;

import static java.lang.Long.parseLong;
import static org.eclipse.jetty.http.HttpStatus.NO_CONTENT_204;
import static org.shulikov.transfer.util.HttpRequestUtil.getRequiredQueryParam;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.shulikov.transfer.service.api.AccountService;

@Singleton
public class AccountController implements CrudHandler {

  private AccountService accountService;

  @Inject
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @Override
  public void create(@NotNull Context context) {
    context.json(accountService.create(getRequiredQueryParam(context, "holderName")));
  }

  @Override
  public void delete(@NotNull Context context, @NotNull String id) {
    accountService.delete(parseLong(id));
    context.status(NO_CONTENT_204);
  }

  @Override
  public void getAll(@NotNull Context context) {
    context.json(accountService.findAll());
  }

  @Override
  public void getOne(@NotNull Context context, @NotNull String id) {
    context.json(accountService.getById(parseLong(id)));
  }

  @Override
  public void update(@NotNull Context context, @NotNull String id) {
    String newHolderName = getRequiredQueryParam(context, "holderName");
    context.json(accountService.update(parseLong(id), newHolderName));
  }
}
