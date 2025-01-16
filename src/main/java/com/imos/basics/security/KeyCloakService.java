package com.imos.basics.security;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * Class KeyCloakService TODO
 *
 * @author Alok Ranjan Meher
 * @since 11-01-2025
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class KeyCloakService {

  private static final String CONNECT_CERTS_URL_FORMAT =
      "http://%s/realms/%s/protocol/openid-connect/certs";
  private static final String ACCESS_TOKEN_URL_FORMAT =
      "http://%s/realms/%s/protocol/openid-connect/token";

  private final KeyCloakProperty keyCloakProperty;

  private JwkProvider jwkProvider;
  private String keyCloakAccessTokenUrl;

  @PostConstruct
  public void setUp() {
    keyCloakAccessTokenUrl =
        String.format(
            ACCESS_TOKEN_URL_FORMAT, keyCloakProperty.getBaseUrl(), keyCloakProperty.getRealm());
    log.debug("KeyCloakAccessTokenUrl: {}", keyCloakAccessTokenUrl);

    String keyCloakCertUrl =
        String.format(
            CONNECT_CERTS_URL_FORMAT, keyCloakProperty.getBaseUrl(), keyCloakProperty.getRealm());
    log.debug("KeyCloakCertUrl: {}", keyCloakCertUrl);
    try {
      jwkProvider = new JwkProviderBuilder(URI.create(keyCloakCertUrl).toURL()).build();
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  public String validateJwtRSA256Token(String token) throws JwtValidationFailedException {
    try {
      DecodedJWT decodedJWT = JWT.decode(token);
      Jwk jwk = jwkProvider.get(decodedJWT.getKeyId());
      //      log.info("Public Key: {}", jwk.getPublicKey());
      //      log.info("Public Key: {}", new String(jwk.getPublicKey().getEncoded()));
      Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey());

      log.debug("Issuer: {}", decodedJWT.getIssuer());
      log.debug("Subject: {}", decodedJWT.getSubject());
      log.debug("Audience: {}", decodedJWT.getAudience());
      JWTVerifier verifier =
          JWT.require(algorithm)
              .withIssuer(decodedJWT.getIssuer())
              .withSubject(decodedJWT.getSubject())
              .withAudience(decodedJWT.getAudience().toArray(new String[0]))
              .build();
      verifier.verify(token);
      return decodedJWT.getSubject();
    } catch (JWTVerificationException | JwkException e) {
      String errorMessage = e.getMessage();
      if (errorMessage.startsWith("The Token's")) {
        errorMessage = "JWT Token is invalid";
      } else if (errorMessage.startsWith("The input")) {
        errorMessage = "JWT Token is invalid";
      } else if (errorMessage.endsWith("JSON format.")) {
        errorMessage = "JWT Token is invalid";
      }
      throw new JwtValidationFailedException(errorMessage);
    }
  }

  public String validateJwtHS256Token(String token) throws JwtValidationFailedException {
    try {
      DecodedJWT decodedJWT = JWT.decode(token);
      Jwk jwk = jwkProvider.get(decodedJWT.getKeyId());
      //      log.info("Public Key: {}", jwk.getPublicKey());
      //      log.info("Public Key: {}", new String(jwk.getPublicKey().getEncoded()));
      Algorithm algorithm = Algorithm.HMAC256(jwk.getPublicKey().getEncoded());

      log.debug("Issuer: {}", decodedJWT.getIssuer());
      log.debug("Subject: {}", decodedJWT.getSubject());
      log.debug("Audience: {}", decodedJWT.getAudience());
      JWTVerifier verifier =
          JWT.require(algorithm)
              .withIssuer(decodedJWT.getIssuer())
              .withSubject(decodedJWT.getSubject())
              .withAudience(decodedJWT.getAudience().toArray(new String[0]))
              .build();
      verifier.verify(token);
      return decodedJWT.getSubject();
    } catch (JWTVerificationException | JwkException e) {
      String errorMessage = e.getMessage();
      if (errorMessage.startsWith("The Token's")) {
        errorMessage = "JWT Token is invalid";
      } else if (errorMessage.startsWith("The input")) {
        errorMessage = "JWT Token is invalid";
      } else if (errorMessage.endsWith("JSON format.")) {
        errorMessage = "JWT Token is invalid";
      }
      throw new JwtValidationFailedException(errorMessage);
    }
  }

  public String fetchAccessTokenWithRefreshToken(String refreshToken) throws KeyCloakException {
    log.info("Refresh token: {}", refreshToken);
    String grantType = "refresh_token";
    String clientId = keyCloakProperty.getClientId();
    String clientSecret = keyCloakProperty.getClientSecret();
    String scope = "email profile";
    Map<String, String> requestBodyMap = new HashMap<>();
    requestBodyMap.put("grant_type", grantType);
    log.info("Grant Type: {}", grantType);
    requestBodyMap.put("refresh_token", refreshToken);
    requestBodyMap.put("client_id", clientId);
    requestBodyMap.put("scope", scope);
    log.info("Client ID: {}", clientId);
    requestBodyMap.put("client_secret", clientSecret);
    String requestBody =
        requestBodyMap.entrySet().stream()
            .map(
                entry ->
                    String.join(
                        "=",
                        URLEncoder.encode(entry.getKey(), UTF_8),
                        URLEncoder.encode(entry.getValue(), UTF_8)))
            .collect(Collectors.joining("&"));
    try (HttpClient httpClient = HttpClient.newHttpClient()) {
      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(URI.create(keyCloakAccessTokenUrl))
              .header("Content-Type", "application/x-www-form-urlencoded")
              .header("Accept", "application/json")
              .POST(HttpRequest.BodyPublishers.ofString(requestBody))
              .build();
      log.debug("Sending HTTP POST request to {}", keyCloakAccessTokenUrl);
      LocalDateTime startTime = LocalDateTime.now();
      HttpResponse<String> response =
          httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      LocalDateTime endTime = LocalDateTime.now();
      log.debug("Received HTTP response {}", response.statusCode());
      log.debug("Received HTTP response status {}", response.statusCode());
      log.info("Time spent: {}", timeSpent(startTime, endTime));
      String responseBody = response.body();
      JSONObject accessTokenBody = new JSONObject(responseBody);
      log.info("Access token body: \n{}", accessTokenBody.toString(4));
      if (response.statusCode() == 200) {
        accessTokenBody.remove("refresh_expires_in");
        accessTokenBody.remove("not-before-policy");
        accessTokenBody.remove("scope");
        accessTokenBody.remove("token_type");
        return accessTokenBody.toString();
      } else {
        throw new Exception(responseBody);
      }
    } catch (Exception e) {
      String errorMessage = e.getMessage();
      if (e instanceof ConnectException) {
        errorMessage =
            new JSONObject()
                .put("errorMessage", "Connect to KeyCloak Server not available")
                .toString();
      }
      throw new KeyCloakException(errorMessage);
    }
  }

  public String fetchAccessToken() throws KeyCloakException {
    String grantType = "client_credentials";
    String clientId = keyCloakProperty.getClientId();
    String clientSecret = keyCloakProperty.getClientSecret();
    Map<String, String> requestBodyMap = new HashMap<>();
    requestBodyMap.put("grant_type", grantType);
    log.info("Grant Type: {}", grantType);
    requestBodyMap.put("client_id", clientId);
    log.info("Client ID: {}", keyCloakProperty.getClientId());
    requestBodyMap.put("client_secret", clientSecret);
    String requestBody =
        requestBodyMap.entrySet().stream()
            .map(
                entry ->
                    String.join(
                        "=",
                        URLEncoder.encode(entry.getKey(), UTF_8),
                        URLEncoder.encode(entry.getValue(), UTF_8)))
            .collect(Collectors.joining("&"));
    try (HttpClient httpClient = HttpClient.newHttpClient()) {
      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(URI.create(keyCloakAccessTokenUrl))
              .header("Content-Type", "application/x-www-form-urlencoded")
              .header("Accept", "application/json")
              .POST(HttpRequest.BodyPublishers.ofString(requestBody))
              .build();
      log.debug("Sending HTTP POST request to {}", keyCloakAccessTokenUrl);
      LocalDateTime startTime = LocalDateTime.now();
      HttpResponse<String> response =
          httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      LocalDateTime endTime = LocalDateTime.now();
      log.debug("Received HTTP response {}", response.statusCode());
      log.debug("Received HTTP response status {}", response.statusCode());
      log.info("Time spent: {}", timeSpent(startTime, endTime));
      String responseBody = response.body();
      if (response.statusCode() == 200) {
        JSONObject accessTokenBody = new JSONObject(responseBody);
        log.info("Access token body: \n{}", accessTokenBody.toString(4));
        accessTokenBody.remove("refresh_expires_in");
        accessTokenBody.remove("not-before-policy");
        accessTokenBody.remove("scope");
        accessTokenBody.remove("token_type");
        return accessTokenBody.toString();
      } else {
        throw new Exception(responseBody);
      }
    } catch (Exception e) {
      String errorMessage = e.getMessage();
      if (e instanceof ConnectException) {
        errorMessage =
            new JSONObject()
                .put("errorMessage", "Connect to KeyCloak Server not available")
                .toString();
      }
      throw new KeyCloakException(errorMessage);
    }
  }

  private String timeSpent(LocalDateTime startTime, LocalDateTime endTime) {
    Duration duration = Duration.between(startTime, endTime);
    if (duration.toMinutes() > 60) {
      log.info("Time spent in {} minutes", duration.toMinutes());
    } else {
      if (duration.toSeconds() > 60) {
        log.info("Time spent in {} seconds", duration.toSeconds());
      } else {
        if (duration.toSeconds() > 1) {
          return duration.toSeconds() + " seconds " + duration.toMillisPart() + " milliseconds";
        } else {
          return duration.toMillis() + " milliseconds";
        }
      }
    }
    return "";
  }
}
