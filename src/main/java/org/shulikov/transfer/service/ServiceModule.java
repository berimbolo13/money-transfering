package org.shulikov.transfer.service;

import com.google.inject.AbstractModule;
import org.shulikov.transfer.service.api.AccountService;
import org.shulikov.transfer.service.api.TransactionService;
import org.shulikov.transfer.service.impl.AccountServiceImpl;
import org.shulikov.transfer.service.impl.TransactionServiceImpl;

public class ServiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(AccountService.class).to(AccountServiceImpl.class);
    bind(TransactionService.class).to(TransactionServiceImpl.class);
  }
}