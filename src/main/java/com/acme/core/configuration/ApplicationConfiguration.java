package com.acme.core.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@Configuration
@ComponentScan("com.acme")
@PropertySource(value = {"classpath:application.properties"},
        ignoreResourceNotFound = true)
public class ApplicationConfiguration {
  private static Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);

}
