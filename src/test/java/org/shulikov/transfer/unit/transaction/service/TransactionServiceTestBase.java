package org.shulikov.transfer.unit.transaction.service;

import static org.mockito.Mockito.reset;

import org.junit.After;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.shulikov.transfer.repository.api.AccountRepository;
import org.shulikov.transfer.service.impl.TransactionServiceImpl;
import org.shulikov.transfer.unit.transaction.TransactionTestBase;
import org.shulikov.transfer.validator.api.TransactionValidator;

public abstract class TransactionServiceTestBase extends TransactionTestBase {

  @InjectMocks
  protected TransactionServiceImpl transactionService;

  @Mock
  protected TransactionValidator transactionValidator;

  @Mock
  protected AccountRepository accountRepository;

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void resetMocks() {
    reset(accountRepository);
  }

}
