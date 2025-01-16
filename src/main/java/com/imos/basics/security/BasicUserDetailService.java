//package com.imos.basics.security;
//
//import java.util.List;
//import javax.sql.DataSource;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.stereotype.Component;
//
///**
// * Class BasicDaoAuthenticationProvider TODO
// *
// * @author Alok Ranjan Meher
// * @since 11-01-2025
// * @version 1.0
// */
//@Slf4j
//@Component
//public class BasicUserDetailService extends JdbcUserDetailsManager {
//
//  public BasicUserDetailService(DataSource dataSource) {
//    super(dataSource);
//  }
//
//  @Override
//  protected List<UserDetails> loadUsersByUsername(String username) {
//    return super.loadUsersByUsername(username);
//  }
//}
