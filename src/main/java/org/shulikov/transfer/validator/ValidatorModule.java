package org.shulikov.transfer.validator;

import com.google.inject.AbstractModule;
import org.shulikov.transfer.validator.api.BalanceValidator;
import org.shulikov.transfer.validator.impl.BalanceValidatorImpl;

public class ValidatorModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(BalanceValidator.class).to(BalanceValidatorImpl.class);
  }
}