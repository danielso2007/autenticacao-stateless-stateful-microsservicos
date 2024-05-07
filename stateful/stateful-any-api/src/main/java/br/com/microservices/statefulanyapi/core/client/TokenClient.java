package br.com.microservices.statefulanyapi.core.client;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import br.com.microservices.statefulanyapi.core.dto.AuthUserResponse;
import br.com.microservices.statefulanyapi.core.dto.TokenDto;

@HttpExchange("/api/auth")
public interface TokenClient {

    @PostExchange("token/validate")
    TokenDto validateToken(@RequestHeader String accessToken);

    @GetExchange("user")
    AuthUserResponse getAuthenticatedUser(@RequestHeader String accessToken);
}
