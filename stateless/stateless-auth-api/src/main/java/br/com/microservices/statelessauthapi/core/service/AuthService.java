package br.com.microservices.statelessauthapi.core.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import br.com.microservices.statelessauthapi.core.dto.AuthResquest;
import br.com.microservices.statelessauthapi.core.dto.TokenDto;
import br.com.microservices.statelessauthapi.core.repository.UserRepository;
import br.com.microservices.statelessauthapi.infra.exception.ValidationException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public TokenDto login(AuthResquest authResquest) {
        var user = userRepository.findByUsername(authResquest.username()).orElseThrow(() -> new ValidationException("User not found!"));
        var accessToken = jwtService.createToken(user);
        validatePassword(authResquest.password(), user.getPassword());
        return new TokenDto(accessToken);
    }

    private void validatePassword(String rawPassword, String encodePassword) {
        if (passwordEncoder.matches(rawPassword, encodePassword)) {
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
