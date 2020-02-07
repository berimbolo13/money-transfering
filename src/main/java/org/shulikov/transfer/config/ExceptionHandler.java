package org.shulikov.transfer.config;

import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.NOT_FOUND_404;

import com.google.inject.Singleton;
import io.javalin.Javalin;
import org.shulikov.transfer.exception.BadRequestException;
import org.shulikov.transfer.exception.ResourceNotFoundException;

@Singleton
public class ExceptionHandler {

  public void bindExceptions(Javalin app) {
    app
        .exception(ResourceNotFoundException.class,
            (e, ctx) -> ctx.status(NOT_FOUND_404).json(e.getMessage()))
        .exception(BadRequestException.class,
            (e, ctx) -> ctx.status(BAD_REQUEST_400).json(e.getMessage()));
  }

}
