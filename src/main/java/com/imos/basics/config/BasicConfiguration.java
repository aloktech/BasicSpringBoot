package com.imos.basics.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.imos.basics.core.RequestResponseLoggingFilter;
import org.json.JSONArray;
import org.json.JSONObject;
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

  @Bean
  public ObjectMapper getObjectMapper() {
    var objectMapper = new ObjectMapper();

    SimpleModule module = new SimpleModule();
    module.addSerializer(JSONObject.class, new JSONObjectSerializer());
    module.addSerializer(JSONArray.class, new JSONArraySerializer());
    objectMapper.registerModule(module);
    objectMapper.registerModule(new Jdk8Module());

    return objectMapper;
  }
}
