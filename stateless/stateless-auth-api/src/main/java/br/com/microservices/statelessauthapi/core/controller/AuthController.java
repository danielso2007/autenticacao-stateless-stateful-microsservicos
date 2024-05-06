package br.com.microservices.statelessauthapi.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.microservices.statelessauthapi.core.dto.AuthResquest;
import br.com.microservices.statelessauthapi.core.dto.TokenDto;
import br.com.microservices.statelessauthapi.core.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("login")
    public  TokenDto login(@RequestBody @Valid AuthResquest resquest) {
        return authService.login(resquest);
    }

    @PostMapping("token/validate")
    public  TokenDto login(@RequestHeader String accessToken) {
        return authService.validateToken(accessToken);
    }

}
