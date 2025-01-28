package com.imos.basics.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Class KeyCloakSecurityFilter TODO
 *
 * @author Alok Ranjan Meher
 * @since 11-01-2025
 * @version 1.0
 */
@Slf4j
@Component
public class AccessTokenVerificationFilter extends OncePerRequestFilter {

  private final KeyCloakService keyCloakService;

  public AccessTokenVerificationFilter(KeyCloakService keyCloakService) {
    this.keyCloakService = keyCloakService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String requestUrl = request.getRequestURI();
    log.debug("AccessTokenVerificationFilter Request URL: {}", requestUrl);
    String authorizationData = request.getHeader("Authorization");
    if (authorizationData != null && authorizationData.startsWith("Bearer ")) {
      String token = authorizationData.substring(7);
      try {
        String subject = keyCloakService.validateJwtRSA256Token(token);

        log.info("Token Validated");

        SecurityContextHolder.getContext()
            .setAuthentication(
                new UsernamePasswordAuthenticationToken(
                    subject, "***", List.of(new SimpleGrantedAuthority("ROLE_USER"))));

        filterChain.doFilter(request, response);
      } catch (JwtValidationFailedException e) {
        log.error("Access Token Validation Failed: {}", e.getMessage());
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String errorMessage = e.getMessage();
        response.getWriter().write(new JSONObject().put("errorMessage", errorMessage).toString());
      }
    } else {
      String errorMessage = "Authorization header is missing";
      log.error(errorMessage);
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.getWriter().write(new JSONObject().put("errorMessage", errorMessage).toString());
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String requestUrl = request.getRequestURI();
    return requestUrl.contains("/login") || requestUrl.contains("/actuator") || requestUrl.contains("/favicon.ico");
  }
}
