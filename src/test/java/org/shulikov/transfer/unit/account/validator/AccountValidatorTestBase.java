package org.shulikov.transfer.unit.account.validator;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.shulikov.transfer.unit.account.AccountTestBase;
import org.shulikov.transfer.validator.impl.AccountValidatorImpl;

public abstract class AccountValidatorTestBase extends AccountTestBase {

  @InjectMocks
  protected AccountValidatorImpl accountValidator;

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }
}
