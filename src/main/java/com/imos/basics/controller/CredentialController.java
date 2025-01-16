//package com.imos.basics.controller;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.json.JSONObject;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * Class CredentialController TODO
// *
// * @author Alok Ranjan Meher
// * @since 11-01-2025
// * @version 1.0
// */
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/v1")
//public class CredentialController {
//
//  @PostMapping(value = "/in-memory/login", produces = MediaType.APPLICATION_JSON_VALUE)
//  public String login() {
//    return new JSONObject().put("status", "Login is successful").toString();
//  }
//}
