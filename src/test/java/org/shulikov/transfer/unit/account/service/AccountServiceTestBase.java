package org.shulikov.transfer.unit.account.service;

import static org.mockito.Mockito.reset;

import org.junit.After;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.shulikov.transfer.repository.api.AccountRepository;
import org.shulikov.transfer.service.impl.AccountServiceImpl;
import org.shulikov.transfer.unit.account.AccountTestBase;

public abstract class AccountServiceTestBase extends AccountTestBase {

  @InjectMocks
  protected AccountServiceImpl accountService;

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
