package com.xische.demo.config;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


/**
 * The type App config.
 */
@Configuration
public class AppConfig {

  /**
   * Rest template rest template.
   *
   * @param builder the builder
   * @return the rest template
   */
  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.connectTimeout(Duration.ofSeconds(5)).readTimeout(Duration.ofSeconds(5)).build();
  }
}

