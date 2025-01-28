package com.imos.basics.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Class BasicProperties TODO
 *
 * @author Alok Ranjan Meher
 * @since 27-01-2025
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "properties")
public class BasicProperties {

  private int offSet;
  private int pageSize;
}
