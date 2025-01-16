//package com.imos.basics.core;
//
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletRequestWrapper;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * Class ControllerLogger TODO
// *
// * @author Alok Ranjan Meher
// * @since 12-01-2025
// * @version 1.0
// */
//@Slf4j
//public class RequestResponseWrapper extends HttpServletRequestWrapper {
//
//  public RequestResponseWrapper(HttpServletRequest request) {
//    super(request);
//  }
//
//  @Override
//  public void setRequest(ServletRequest request) {
//    super.setRequest(request);
//    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//    log.info("Endpoint: {} called", httpServletRequest.getRequestURI());
//  }
//
//  @Override
//  public ServletRequest getRequest() {
//    HttpServletRequest httpServletRequest = (HttpServletRequest) super.getRequest();
//    log.info("Endpoint: {} called", httpServletRequest.getRequestURI());
//    return httpServletRequest;
//  }
//}
