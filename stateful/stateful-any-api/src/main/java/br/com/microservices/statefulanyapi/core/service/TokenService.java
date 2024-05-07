package br.com.microservices.statefulanyapi.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import br.com.microservices.statefulanyapi.core.client.TokenClient;
import br.com.microservices.statefulanyapi.core.dto.AuthUserResponse;
import br.com.microservices.statefulanyapi.infra.exception.AuthenticationException;

@Slf4j
@Service
@AllArgsConstructor
public class TokenService {

    private final TokenClient tokenClient;

    public void validateToken(String token) {
        try {
            log.info("Sending request for token {}", token.replaceAll("[\r\n]", ""));
            var response = tokenClient.validateToken(token);
            log.info("Token is valid: {}", response.toString().replaceAll("[\r\n]", ""));
        } catch (Exception ex) {
            String msg = null;
            if (ex instanceof WebClientResponseException) {
                if (((WebClientResponseException) ex).getStatusText().equals("Unauthorized")) {
                    msg = "Invalid token!";
                }
            } else {
                msg = "Auth error: " + ex.getMessage();
            }
            throw new AuthenticationException(msg);
        }
    }

    public AuthUserResponse getAuthenticatedUser(String token) {
        try {
            log.info("Sending request for auth user {}", token.replaceAll("[\r\n]", ""));
            var response = tokenClient.getAuthenticatedUser(token);
            if (ObjectUtils.isEmpty(response) || ObjectUtils.isEmpty(response.id())) {
                throw new AuthenticationException("User is not found.");
            }
            log.info("Auth user found: {} and token {}", response.toString().replaceAll("[\r\n]", ""),
                    token.replaceAll("[\r\n]", ""));
            return response;
        } catch (Exception ex) {
            throw new AuthenticationException("Error to get authenticated user!");
        }
    }
}
