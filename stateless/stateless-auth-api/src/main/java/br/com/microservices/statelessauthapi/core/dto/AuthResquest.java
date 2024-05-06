package br.com.microservices.statelessauthapi.core.dto;

import jakarta.validation.constraints.NotNull;

public record AuthResquest(@NotNull(message = "Username not be null") String username,
        @NotNull(message = "Password not be null") String password) {

}
