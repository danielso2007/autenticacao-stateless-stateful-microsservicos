package br.com.microservices.statelessauthapi.infra.exception;

public record ExceptionDetails(int status, Object message) {
}
