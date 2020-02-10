package org.shulikov.transfer.integration.account;

import static kong.unirest.Unirest.delete;
import static kong.unirest.Unirest.get;
import static kong.unirest.Unirest.patch;
import static kong.unirest.Unirest.post;
import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.NOT_FOUND_404;
import static org.eclipse.jetty.http.HttpStatus.NO_CONTENT_204;
import static org.eclipse.jetty.http.HttpStatus.OK_200;

import kong.unirest.HttpResponse;
import org.junit.Test;
import org.shulikov.transfer.model.Account;

public class AccountIntegrationTest extends AccountIntegrationTestBase {

  @Test
  public void create_account_returns201() {
    Account account = createAccount();
    assertThat((account).getHolderName()).isEqualTo(DEFAULT_HOLDER_NAME);
  }

  @Test
  public void create_account_holderNameIsNotSpecified_returns400() {
    HttpResponse<String> response = post(API_ACCOUNTS).asString();

    assertThat((response.getStatus())).isEqualTo(BAD_REQUEST_400);
    assertThat((response.getBody())).isEqualTo(HOLDER_NAME_IS_NOT_SPECIFIED);
  }

  @Test
  public void delete_existedAccount_returns_204() {
    String accountUri = buildAccountUri(createAccount().getId());

    HttpResponse<String> deletionResponse = delete(accountUri).asString();
    assertThat(deletionResponse.getStatus()).isEqualTo(NO_CONTENT_204);

    HttpResponse<String> getByIdResponse = get(accountUri).asString();
    assertThat(getByIdResponse.getStatus()).isEqualTo(NOT_FOUND_404);
  }

  @Test
  public void delete_nonExistedAccount_returns404() {
    HttpResponse<String> deletionResponse = delete(buildAccountUri(NON_EXISTED_ID)).asString();

    assertThat(deletionResponse.getStatus()).isEqualTo(NOT_FOUND_404);
    assertThat(deletionResponse.getBody()).isEqualTo(ACCOUNT_NOT_FOUND);
  }

  @Test
  public void fetch_preseeded_accounts_returns200_withAccounts() {
    HttpResponse<Account[]> response = get(API_ACCOUNTS).asObject(Account[].class);

    assertThat((response.getStatus())).isEqualTo(OK_200);
    assertThat(response.getBody().length).isGreaterThan(0);
  }

  @Test
  public void get_existedAccount_byId_returns200_withAccount() {
    Account createdAccount = createAccount();

    HttpResponse<Account> getByIdResponse =
        get(buildAccountUri(createdAccount.getId())).asObject(Account.class);

    assertThat(getByIdResponse.getStatus()).isEqualTo(OK_200);
    assertThat(getByIdResponse.getBody().getId()).isEqualTo(createdAccount.getId());
  }

  @Test
  public void get_nonExistedAccount_byId_returns404() {
    HttpResponse<String> response = get(buildAccountUri(NON_EXISTED_ID)).asString();

    assertThat(response.getStatus()).isEqualTo(NOT_FOUND_404);
    assertThat(response.getBody()).isEqualTo(ACCOUNT_NOT_FOUND);
  }

  @Test
  public void update_account_returns_200_for_presentedQueryParam() {
    Account account = createAccount();
    String newName = "newName";

    HttpResponse<Account> updateResponse =
        patch(buildAccountUri(account.getId()))
            .queryString(HOLDER_NAME_PARAM, newName)
            .asObject(Account.class);

    assertThat(updateResponse.getStatus()).isEqualTo(OK_200);
    assertThat(updateResponse.getBody().getHolderName()).isEqualTo(newName);

  }

  @Test
  public void update_account_forExistedAccount_holderNameNull_returns400() {
    Account account = createAccount();
    HttpResponse<String> updateResponse = patch(buildAccountUri(account.getId())).asString();

    assertThat(updateResponse.getStatus()).isEqualTo(BAD_REQUEST_400);
    assertThat(updateResponse.getBody()).isEqualTo(HOLDER_NAME_IS_NOT_SPECIFIED);
  }

  @Test
  public void update_account_forNonExistedAccount_returns404() {
    HttpResponse<String> response =
        patch(buildAccountUri(NON_EXISTED_ID))
            .queryString(HOLDER_NAME_PARAM, DEFAULT_HOLDER_NAME)
            .asString();

    assertThat(response.getStatus()).isEqualTo(NOT_FOUND_404);
    assertThat(response.getBody()).isEqualTo(ACCOUNT_NOT_FOUND);
  }

}