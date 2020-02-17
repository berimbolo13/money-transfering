package org.shulikov.transfer.config;

import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.NOT_FOUND_404;

import com.google.inject.Singleton;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;
import org.shulikov.transfer.exception.BadRequestException;
import org.shulikov.transfer.exception.ResourceNotFoundException;

@Singleton
@Slf4j
public class ExceptionHandler {

  public void bindExceptions(Javalin app) {
    app
        .exception(ResourceNotFoundException.class,
            (e, ctx) -> {
              log.error(e.getMessage(), e);
              ctx.status(NOT_FOUND_404).json(e.getMessage());
            })
        .exception(BadRequestException.class,
            (e, ctx) -> {
              log.error(e.getMessage(), e);
              ctx.status(BAD_REQUEST_400).json(e.getMessage());
            });
  }

}
