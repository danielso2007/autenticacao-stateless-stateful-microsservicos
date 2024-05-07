package br.com.microservices.statefulauthapi.core.controller;

import lombok.AllArgsConstructor;
import br.com.microservices.statefulauthapi.core.dto.AuthRequest;
import br.com.microservices.statefulauthapi.core.dto.AuthUserResponse;
import br.com.microservices.statefulauthapi.core.dto.TokenDto;
import br.com.microservices.statefulauthapi.core.service.AuthService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService service;

    @PostMapping("login")
    public TokenDto login(@RequestBody AuthRequest request) {
        return service.login(request);
    }

    @PostMapping("token/validate")
    public TokenDto validateToken(@RequestHeader String accessToken) {
        return service.validateToken(accessToken);
    }

    @PostMapping("logout")
    public Map<String, Object> logout(@RequestHeader String accessToken) {
        service.logout(accessToken);
        var response = new HashMap<String, Object>();
        response.put("status", "OK");
        response.put("code", 200);
        return response;
    }

    @GetMapping("user")
    public AuthUserResponse getAuthenticatedUser(@RequestHeader String accessToken) {
        return service.getAuthenticatedUser(accessToken);
    }
}
