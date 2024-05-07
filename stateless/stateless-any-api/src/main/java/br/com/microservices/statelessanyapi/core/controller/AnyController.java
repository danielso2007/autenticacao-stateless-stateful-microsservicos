package br.com.microservices.statelessanyapi.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.microservices.statelessanyapi.core.dto.AnyResponse;
import br.com.microservices.statelessanyapi.core.service.AnyService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/resource")
public class AnyController {

    private final AnyService anyService;

    @GetMapping
    public AnyResponse getResource(@RequestHeader String accessToken) {
        return anyService.getData(accessToken);
    }

}
