package com.acme.core.configuration;

import com.datastax.oss.driver.api.core.CqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.cassandra.core.convert.CassandraCustomConversions;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;


@Configuration
@Import({ApplicationConfiguration.class})
@EnableCassandraRepositories(basePackages = {"com.acme"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ContextConfiguration
@PropertySource(value = {"classpath:application.properties", "classpath:applicationTest.properties"},
        ignoreResourceNotFound = true)
@Profile("IntegrationTest")
public class ApplicationContextTest {
  private static Logger logger = LoggerFactory.getLogger(ApplicationContextTest.class);

  /**
   * to have application.conf loaded with request logger activation (See application.conf)
   */
  public @Bean
  CqlSession session() {
    return CqlSession.builder().build();
  }

  @Bean
  CassandraCustomConversions cassandraCustomConversions(List<Converter<?, ?>> converters) {
    return new CassandraCustomConversions(converters);
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }
}
