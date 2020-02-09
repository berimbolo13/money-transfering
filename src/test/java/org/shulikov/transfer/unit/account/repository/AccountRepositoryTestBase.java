package org.shulikov.transfer.unit.account.repository;

import static org.mockito.Mockito.reset;

import org.junit.After;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.shulikov.transfer.repository.impl.AccountRepositoryImpl;
import org.shulikov.transfer.unit.account.AccountTestBase;
import org.shulikov.transfer.validator.api.AccountValidator;

public abstract class AccountRepositoryTestBase extends AccountTestBase {

  @InjectMocks
  protected AccountRepositoryImpl accountRepository;

  @Mock
  protected AccountValidator accountValidator;

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void resetMocks() {
    reset(accountValidator);
  }

}
