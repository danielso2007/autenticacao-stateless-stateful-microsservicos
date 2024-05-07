package br.com.microservices.statelessanyapi.core.dto;

import jakarta.validation.constraints.NotNull;

public record TokenDto(@NotNull(message = "AccessToken not be null") String accessToken) {
    
}
