package com.imos.basics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class BasicSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(BasicSpringBootApplication.class, args);
  }
}
