package com.allaroundjava.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.allaroundjava.service", "com.allaroundjava.controller"})
public class AppConfig {
}
