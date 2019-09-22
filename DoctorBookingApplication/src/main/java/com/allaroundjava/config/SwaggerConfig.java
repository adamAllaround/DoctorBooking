package com.allaroundjava.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {
    @Value("${app.swagger-ui.redirectPrefix}")
    private String redirectPrefix;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController(redirectPrefix + "/v2/api-docs", "/v2/api-docs");
        registry.addRedirectViewController(
                redirectPrefix + "/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
        registry.addRedirectViewController(
                redirectPrefix + "/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
        registry.addRedirectViewController(redirectPrefix + "/swagger-resources", "/swagger-resources");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/" + redirectPrefix + "/swagger-ui.html**")
                .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
        registry.addResourceHandler("/" + redirectPrefix + "/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
