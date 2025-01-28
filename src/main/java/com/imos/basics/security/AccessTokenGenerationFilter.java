package com.imos.basics.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Class AccessTokenGenerationFilter TODO
 *
 * @author Alok Ranjan Meher
 * @since 11-01-2025
 * @version 1.0
 */
@Slf4j
@Component
public class AccessTokenGenerationFilter extends OncePerRequestFilter {

  private final KeyCloakService keyCloakService;

  public AccessTokenGenerationFilter(KeyCloakService keyCloakService) {
    this.keyCloakService = keyCloakService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String requestUrl = request.getRequestURI();
    log.debug("AccessTokenGenerationFilter Request URL: {}", requestUrl);
    try {
      String requestBody = request.getReader().lines().collect(Collectors.joining());
      String responseBody;
      if (requestBody.isBlank() || !requestBody.startsWith("refresh_token=")) {
        responseBody = keyCloakService.fetchAccessToken();
      } else {
        log.info(requestBody);
        String refreshToken = requestBody.substring("refresh_token=".length());
        responseBody = keyCloakService.fetchAccessTokenWithRefreshToken(refreshToken);
      }
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setStatus(HttpServletResponse.SC_OK);
      response.getWriter().write(responseBody);
    } catch (Exception e) {
      String errorMessage = e.getMessage();
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      response.getWriter().write(errorMessage);
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String requestUrl = request.getRequestURI();
    return !requestUrl.contains("/login")
        && !(requestUrl.contains("/actuator/**") || requestUrl.contains("/favicon.ico"));
  }
}
