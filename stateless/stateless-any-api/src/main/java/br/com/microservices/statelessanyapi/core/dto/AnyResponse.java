package br.com.microservices.statelessanyapi.core.dto;

public record AnyResponse(String status, Integer code, AuthUserResponse authUserResponse) {
    
}
