package com.imos.basics.config;

import com.imos.basics.core.RequestResponseLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class BasicConfiguration TODO
 *
 * @author Alok Ranjan Meher
 * @since 12-01-2025
 * @version 1.0
 */
@Configuration
public class BasicConfiguration {

  @Bean
  public FilterRegistrationBean<RequestResponseLoggingFilter> loggingFilter() {
    FilterRegistrationBean<RequestResponseLoggingFilter> registrationBean =
        new FilterRegistrationBean<>();

    registrationBean.setFilter(new RequestResponseLoggingFilter());
    registrationBean.addUrlPatterns("/api/*");
    registrationBean.setOrder(1);

    return registrationBean;
  }
}
