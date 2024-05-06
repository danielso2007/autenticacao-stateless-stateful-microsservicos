package br.com.microservices.statelessauthapi.core.service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import br.com.microservices.statelessauthapi.infra.exception.AuthenticationException;
import br.com.microservices.statelessauthapi.infra.exception.ValidationException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import br.com.microservices.statelessauthapi.core.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final Integer TOKEN_INDEX = 1;
    private static final String EMPTY_SPACE = " ";
    private static final Integer ONE_DAY_IN_HOURS = 24;

    @Value("${app.token.private-key}")
    private String privateKey;

    @Value("${app.token.public-key}")
    private String publicKey;

    public String createToken(User user) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        Map<String, String> data = new HashMap<>();
        data.put("id", user.getId().toString());
        data.put("username", user.getUsername());
        return Jwts.builder().claims(data).expiration(generateExpiresAt())
                .signWith(generateJwtKeyEncryption(privateKey)).compact();
    }

    private Date generateExpiresAt() {
        return Date.from(LocalDateTime.now().plusHours(ONE_DAY_IN_HOURS).atZone(ZoneId.systemDefault()).toInstant());
    }

    public void validateAccessToken(String token) {
        var accessToken = extractToken(token);
        try {
            Jwts.parser().verifyWith(generateJwtKeyDecryption(publicKey)).build().parseSignedClaims(accessToken)
                    .getPayload();
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

    public PublicKey generateJwtKeyDecryption(String jwtPublicKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] keyBytes = Base64.decodeBase64(jwtPublicKey);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        return keyFactory.generatePublic(x509EncodedKeySpec);
    }

    public PrivateKey generateJwtKeyEncryption(String jwtPrivateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] keyBytes = Base64.decodeBase64(jwtPrivateKey);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }

}
