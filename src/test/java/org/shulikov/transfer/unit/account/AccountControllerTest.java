package org.shulikov.transfer.unit.account;

import static org.eclipse.jetty.http.HttpStatus.CREATED_201;
import static org.eclipse.jetty.http.HttpStatus.NO_CONTENT_204;
import static org.eclipse.jetty.http.HttpStatus.OK_200;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.shulikov.transfer.exception.BadRequestException;
import org.shulikov.transfer.exception.ResourceNotFoundException;
import org.shulikov.transfer.model.Account;

public class AccountControllerTest extends AccountControllerTestBase {

  @Test
  public void POST_to_create_account_gives_201_for_presentedQueryParam() {
    Account account = createAccount();
    when(ctx.queryParam(HOLDER_NAME_FIELD)).thenReturn(account.getHolderName());
    when(accountService.create(account.getHolderName())).thenReturn(account);

    accountController.create(ctx);
    verify(ctx).status(CREATED_201);
  }

  @Test(expected = BadRequestException.class)
  public void POST_to_create_account_throwsBadRequest_for_nullQueryParam() {
    when(ctx.queryParam(HOLDER_NAME_FIELD)).thenReturn(null);
    accountController.create(ctx);
  }

  @Test
  public void DELETE_to_delete_account_gives_204_for_existedAccount() {
    accountController.delete(ctx, "1");
    verify(ctx).status(NO_CONTENT_204);
  }

  @Test(expected = ResourceNotFoundException.class)
  public void DELETE_to_delete_account_throwsNotFound_for_nonExistedAccount() {
    doThrow(new ResourceNotFoundException("Account with id 1 was not found"))
        .when(accountService).delete(1L);
    accountController.delete(ctx, "1");
  }

  @Test
  public void GET_to_getAllAccounts_gives_200() {
    accountController.getAll(ctx);
    verify(ctx).status(OK_200);
  }

  @Test
  public void GET_to_get_account_gives_200_for_existedAccount() {
    Account account = createAccount();
    when(accountService.getById(account.getId())).thenReturn(account);

    accountController.getOne(ctx, "1");
    verify(ctx).status(OK_200);
  }

  @Test(expected = ResourceNotFoundException.class)
  public void GET_to_get_account_throwsNotFound_for_nonExistedAccount() {
    doThrow(new ResourceNotFoundException("Account with id 1 was not found"))
        .when(accountService).getById(1L);

    accountController.getOne(ctx, "1");
    verify(ctx).status(OK_200);
  }

  @Test
  public void PATCH_to_update_account_gives_200_for_presentedQueryParam() {
    Account account = createAccount();

    when(ctx.queryParam(HOLDER_NAME_FIELD)).thenReturn(account.getHolderName());
    when(accountService.update(1L, account.getHolderName())).thenReturn(account);

    accountController.update(ctx, "1");
    verify(ctx).status(OK_200);
  }

  @Test(expected = BadRequestException.class)
  public void PATCH_to_update_account_throwsBadRequest_for_nullQueryParam() {
    when(ctx.queryParam(HOLDER_NAME_FIELD)).thenReturn(null);
    accountController.create(ctx);
  }

  @Test(expected = ResourceNotFoundException.class)
  public void PATCH_to_update_account_throwsNotFound_for_nonExistedAccount() {
    when(ctx.queryParam(HOLDER_NAME_FIELD)).thenReturn("Roland");
    doThrow(new ResourceNotFoundException("Account with id 1 was not found"))
        .when(accountService).update(1L, "Roland");

    accountController.update(ctx, "1");
  }

}
