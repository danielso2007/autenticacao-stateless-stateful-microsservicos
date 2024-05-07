package br.com.microservices.statefulanyapi.core.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import br.com.microservices.statefulanyapi.core.dto.AnyResponse;

@Service
@AllArgsConstructor
public class AnyService {

    private final TokenService tokenService;

    public AnyResponse getData(String accessToken) {
        tokenService.validateToken(accessToken);
        var authUser = tokenService.getAuthenticatedUser(accessToken);
        var ok = HttpStatus.OK;
        return new AnyResponse(ok.name(), ok.value(), authUser);
    }
}
