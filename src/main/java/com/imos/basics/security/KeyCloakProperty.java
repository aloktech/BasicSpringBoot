package com.imos.basics.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Class SecurityProperty TODO
 *
 * @author Alok Ranjan Meher
 * @since 11-01-2025
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeyCloakProperty {

  private String baseUrl;
  private String realm;
  private String clientId;
  private String clientSecret;
}
