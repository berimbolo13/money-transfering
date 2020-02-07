package org.shulikov.transfer.repository;

import com.google.inject.AbstractModule;
import org.shulikov.transfer.repository.impl.AccountRepositoryImpl;

public class RepositoryModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(org.shulikov.transfer.repository.api.AccountRepository.class)
        .to(AccountRepositoryImpl.class);
  }
}