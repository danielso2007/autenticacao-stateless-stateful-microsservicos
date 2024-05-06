package br.com.microservices.statelessauthapi.core.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import br.com.microservices.statelessauthapi.core.dto.AuthResquest;
import br.com.microservices.statelessauthapi.core.dto.TokenDto;
import br.com.microservices.statelessauthapi.core.repository.UserRepository;
import br.com.microservices.statelessauthapi.infra.exception.AuthenticationException;
import br.com.microservices.statelessauthapi.infra.exception.ValidationException;
import io.jsonwebtoken.security.InvalidKeyException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public TokenDto login(AuthResquest authResquest) {
        var user = userRepository.findByUsername(authResquest.username()).orElseThrow(() -> new ValidationException("User not found!"));
        validatePassword(authResquest.password(), user.getPassword());
        String accessToken;
        try {
            accessToken = jwtService.createToken(user);
            return new TokenDto(accessToken);
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AuthenticationException(e);
        }
    }

    private void validatePassword(String rawPassword, String encodePassword) {
        if (ObjectUtils.isEmpty(rawPassword)) {
            throw new ValidationException("The password must be informed!");
        }
        if (!passwordEncoder.matches(rawPassword, encodePassword)) {
            throw new ValidationException("The password is incorrect!");
        }
    }

    public TokenDto validateToken(String accessToken) {
        validateExistingToken(accessToken);
        jwtService.validateAccessToken(accessToken);
        return new TokenDto(accessToken);
    }   

    private void validateExistingToken(String accessToken) {
        if (ObjectUtils.isEmpty(accessToken)) {
            throw new ValidationException("The access token must be informed!");
        }
    }

}
