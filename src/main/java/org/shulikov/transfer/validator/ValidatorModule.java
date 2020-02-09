package org.shulikov.transfer.validator;

import com.google.inject.AbstractModule;
import org.shulikov.transfer.validator.api.AccountValidator;
import org.shulikov.transfer.validator.api.TransactionValidator;
import org.shulikov.transfer.validator.impl.AccountValidatorImpl;
import org.shulikov.transfer.validator.impl.TransactionValidatorImpl;

public class ValidatorModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(AccountValidator.class).to(AccountValidatorImpl.class);
    bind(TransactionValidator.class).to(TransactionValidatorImpl.class);
  }
}