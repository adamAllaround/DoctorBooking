package com.allaroundjava.controller;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwaggerController {

    @RequestMapping(method = RequestMethod.GET, path = "/v2/api-docs", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource apiDocs() {
        return new ClassPathResource("swagger.json");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/swagger-resources", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object resources() {
        return ImmutableList.of(ImmutableMap.of(
                "name", "default",
                "url", "/v2/api-docs",
                "location", "/v2/api-docs", // should match the endpoint exposing Swagger JSON
                "swaggerVersion", "2.0"));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/swagger-resources/configuration/security", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object securityConfig() {
        return ImmutableList.of(ImmutableMap.of(
                "apiKeyVehicle", "header",
                "scopeSeparator", ",",
                "apiKeyName", "api_key"));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/swagger-resources/configuration/ui", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object uiConfig() {
        return "{\"deepLinking\":true," +
                "\"displayOperationId\":false," +
                "\"defaultModelsExpandDepth\":1," +
                "\"defaultModelExpandDepth\":1," +
                "\"defaultModelRendering\":\"example\"," +
                "\"displayRequestDuration\":false," +
                "\"docExpansion\":\"none\"," +
                "\"filter\":false," +
                "\"operationsSorter\":\"alpha\"," +
                "\"showExtensions\":false," +
                "\"tagsSorter\":\"alpha\"," +
                "\"validatorUrl\":\"\"," +
                "\"apisSorter\":\"alpha\"," +
                "\"jsonEditor\":false," +
                "\"showRequestHeaders\":false," +
                "\"supportedSubmitMethods\":[\"get\",\"put\",\"post\",\"delete\",\"options\",\"head\",\"patch\",\"trace\"]}";
    }
}