package br.com.microservices.statelessauthapi.core.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import br.com.microservices.statelessauthapi.infra.exception.AuthenticationException;
import br.com.microservices.statelessauthapi.infra.exception.ValidationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import br.com.microservices.statelessauthapi.core.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final Integer TOKEN_INDEX = 1;
    private static final String EMPTY_SPACE = " ";
    private static final Integer ONE_DAY_IN_HOURS = 24;

    @Value("${app.token.secret-key}")
    private String secretKey;

    public String createToken(User user) {
        Map<String, String> data = new HashMap<>();
        data.put("id", user.getId().toString());
        data.put("username", user.getUsername());
        return Jwts.builder().claims(data).expiration(generateExpiresAt()).signWith(generateSign()).compact();
    }

    private Date generateExpiresAt() {
        return Date.from(LocalDateTime.now().plusHours(ONE_DAY_IN_HOURS).atZone(ZoneId.systemDefault()).toInstant());
    }

    private SecretKey generateSign() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public void validateAccessToken(String token) {
        var accessToken = extractToken(token);
        try {
            Jwts.parser().verifyWith(generateSign()).build().parseSignedClaims(accessToken).getPayload();
        } catch (Exception ex) {
            throw new AuthenticationException("Invalid token " + ex.getMessage());
        }
    }

    private String extractToken(String token) {
        if (ObjectUtils.isEmpty(token)) {
            throw new ValidationException("The access token was not informed.");
        }
        if (token.contains(EMPTY_SPACE)) {
            return token.split(EMPTY_SPACE)[TOKEN_INDEX];
        }
        return token;
    }

}
