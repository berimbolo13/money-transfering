package org.shulikov.transfer.unit.transaction.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

import io.javalin.http.Context;
import org.junit.After;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.shulikov.transfer.controller.TransactionController;
import org.shulikov.transfer.service.api.TransactionService;
import org.shulikov.transfer.unit.transaction.TransactionTestBase;

public abstract class TransactionControllerTestBase extends TransactionTestBase {

  @InjectMocks
  protected TransactionController transactionController;

  @Mock
  protected TransactionService transactionService;

  protected Context ctx = mock(Context.class);

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void resetMocks() {
    reset(transactionService);
    reset(ctx);
  }
}
