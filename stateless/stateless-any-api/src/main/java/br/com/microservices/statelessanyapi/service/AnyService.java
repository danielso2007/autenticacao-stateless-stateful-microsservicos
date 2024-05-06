package br.com.microservices.statelessanyapi.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import br.com.microservices.statelessanyapi.core.dto.AnyResponse;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AnyService {

    private final JwtService jwtService;

    public AnyResponse getData(String accessToken) {
        jwtService.validateAccessToken(accessToken);
        var authUser = jwtService.getAuthenticateUser(accessToken);
        var ok = HttpStatus.OK;
        return new AnyResponse(ok.name(), ok.value(), authUser);
    }

}
