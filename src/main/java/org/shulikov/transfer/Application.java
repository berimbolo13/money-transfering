package org.shulikov.transfer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.shulikov.transfer.config.AppModule;
import org.shulikov.transfer.config.Starter;

public class Application {

  public static void main(String[] args) {
    Injector injector = Guice.createInjector(new AppModule());
    injector.getInstance(Starter.class).boot();
  }
}
