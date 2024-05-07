package br.com.microservices.statefulauthapi.core.dto;

import jakarta.validation.constraints.NotNull;

public record AuthRequest(@NotNull(message = "Username not be null") String username,
        @NotNull(message = "Password not be null") String password) {

}
