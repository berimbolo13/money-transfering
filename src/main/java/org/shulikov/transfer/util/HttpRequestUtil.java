package org.shulikov.transfer.util;

import static java.util.Optional.ofNullable;

import io.javalin.http.Context;
import org.shulikov.transfer.exception.BadRequestException;

public class HttpRequestUtil {

  public static String getRequiredQueryParam(Context context, String paramName) {
    return ofNullable(context.queryParam(paramName))
        .orElseThrow(() -> new BadRequestException(paramName + " must be specified"));
  }

}
