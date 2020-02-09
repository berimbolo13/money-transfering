package org.shulikov.transfer.unit.account;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

import io.javalin.http.Context;
import org.junit.After;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.shulikov.transfer.controller.AccountController;
import org.shulikov.transfer.model.Account;
import org.shulikov.transfer.service.api.AccountService;

public abstract class AccountControllerTestBase {

  protected final String HOLDER_NAME_FIELD = "holderName";

  @InjectMocks
  protected AccountController accountController;

  @Mock
  protected AccountService accountService;

  protected Context ctx = mock(Context.class);

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void resetMocks() {
    reset(accountService);
    reset(ctx);
  }

  protected Account createAccount() {
    return new Account(1L, "Roland", 0);
  }
}
