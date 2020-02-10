package org.shulikov.transfer.integration;

import static com.google.inject.Guice.createInjector;
import static java.lang.String.format;
import static kong.unirest.Unirest.post;
import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.jetty.http.HttpStatus.CREATED_201;
import static org.shulikov.transfer.config.Starter.SERVICE_PORT;

import kong.unirest.HttpResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.shulikov.transfer.config.AppModule;
import org.shulikov.transfer.config.Starter;
import org.shulikov.transfer.model.Account;

public abstract class IntegrationTestBase {

  protected static Starter starter = createInjector(new AppModule()).getInstance(Starter.class);

  protected final String SERVER_ADDRESS = "http://localhost:" + SERVICE_PORT;

  protected final String API_ACCOUNTS = SERVER_ADDRESS + "/api/accounts";

  protected final String API_TRANSACTIONS = SERVER_ADDRESS + "/api/transactions";

  protected final String HOLDER_NAME_PARAM = "holderName";

  protected final String DEFAULT_HOLDER_NAME = "Alex";

  protected final Long NON_EXISTED_ID = 999L;

  protected final String ACCOUNT_NOT_FOUND = format("\"Account with id %s was not found\"", NON_EXISTED_ID);

  @BeforeClass
  public static void startApp() throws InterruptedException {
    Thread.sleep(3000);
    starter.boot();
  }

  @AfterClass
  public static void shutdown() {
    starter.stop();
  }

  protected Account createAccount() {
    HttpResponse<Account> response =
        post(API_ACCOUNTS)
            .queryString(HOLDER_NAME_PARAM, DEFAULT_HOLDER_NAME)
            .asObject(Account.class);

    assertThat((response.getStatus())).isEqualTo(CREATED_201);
    return response.getBody();
  }

  protected String buildAccountUri(Long accountId) {
    return format(API_ACCOUNTS + "/%s", accountId);
  }
}
