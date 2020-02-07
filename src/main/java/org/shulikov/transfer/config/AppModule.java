package org.shulikov.transfer.config;

import com.google.inject.AbstractModule;
import org.shulikov.transfer.repository.RepositoryModule;
import org.shulikov.transfer.service.ServiceModule;
import org.shulikov.transfer.validator.ValidatorModule;

public class AppModule extends AbstractModule {

  protected void configure() {
    install(new RepositoryModule());
    install(new ServiceModule());
    install(new ValidatorModule());
  }
}