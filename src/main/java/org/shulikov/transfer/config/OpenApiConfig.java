package org.shulikov.transfer.config;

import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;

public class OpenApiConfig {

  public static OpenApiPlugin getConfiguredOpenApiPlugin() {
    Info info = new Info().version("1.0").description("User API");
    OpenApiOptions options = new OpenApiOptions(info)
        .path("/swagger-docs")
        .swagger(new SwaggerOptions("/swagger-ui"));
    return new OpenApiPlugin(options);
  }
}
