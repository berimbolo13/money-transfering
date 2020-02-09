package org.shulikov.transfer.unit.transaction.validator;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.shulikov.transfer.unit.transaction.TransactionTestBase;
import org.shulikov.transfer.validator.impl.TransactionValidatorImpl;

public abstract class TransactionValidatorTestBase extends TransactionTestBase {

  @InjectMocks
  protected TransactionValidatorImpl transactionValidator;

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }
}
