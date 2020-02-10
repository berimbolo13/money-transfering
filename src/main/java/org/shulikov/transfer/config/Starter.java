package org.shulikov.transfer.config;

import static org.shulikov.transfer.config.OpenApiConfig.getConfiguredOpenApiPlugin;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.javalin.Javalin;

@Singleton
public class Starter {

  public static final int SERVICE_PORT = 7000;

  private Router router;

  private ExceptionHandler exceptionHandler;

  private Javalin app;

  @Inject
  public Starter(Router router, ExceptionHandler exceptionHandler) {
    this.router = router;
    this.exceptionHandler = exceptionHandler;
  }

  public void boot() {
    app = Javalin
        .create(config -> config.registerPlugin(getConfiguredOpenApiPlugin()))
        .start(SERVICE_PORT);
    router.bindRoutes(app);
    exceptionHandler.bindExceptions(app);
  }

  public void stop() {
    app.stop();
  }
}
