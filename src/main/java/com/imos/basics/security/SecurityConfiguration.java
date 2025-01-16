package com.imos.basics.security;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Class SecurityConfiguration TODO
 *
 * @author Alok Ranjan Meher
 * @since 11-01-2025
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

  private final AccessTokenVerificationFilter accessTokenVerificationFilter;
  private final AccessTokenGenerationFilter accessTokenGenerationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(disableCsrf())
        .authorizeHttpRequests(configureAuthorization())
        .httpBasic(Customizer.withDefaults())
        .formLogin(disableFormLogin())
        .addFilterBefore(accessTokenVerificationFilter, BasicAuthenticationFilter.class)
        .addFilterAfter(accessTokenGenerationFilter, BasicAuthenticationFilter.class);

    return http.build();
  }

  private static Customizer<FormLoginConfigurer<HttpSecurity>> disableFormLogin() {
    return AbstractHttpConfigurer::disable;
  }

  private static Customizer<
          AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>
      configureAuthorization() {
    return authorize -> authorize.anyRequest().authenticated();
  }

  private static Customizer<CsrfConfigurer<HttpSecurity>> disableCsrf() {
    return httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.ignoringRequestMatchers("/api/**");
  }

  //  @Bean
  //  @ConditionalOnMissingBean(UserDetailsService.class)
  //  public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder)
  // {
  //    String generatedPassword = passwordEncoder.encode("password");
  //    return new InMemoryUserDetailsManager(
  //        User.withUsername("user").password(generatedPassword).roles("USER").build());
  //  }

  @Bean
  @ConditionalOnMissingBean(UserDetailsService.class)
  public JdbcUserDetailsManager jdbcUserDetailsManager(
      PasswordEncoder passwordEncoder, DataSource dataSource) {
    String generatedPassword = passwordEncoder.encode("password");
    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
    jdbcUserDetailsManager.setDataSource(dataSource);
    try {
      jdbcUserDetailsManager.createUser(
          User.builder().username("user").password(generatedPassword).build());
    } catch (Exception e) {
      log.error("Failed to add new user: {}", e.getMessage());
    }
    return jdbcUserDetailsManager;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
