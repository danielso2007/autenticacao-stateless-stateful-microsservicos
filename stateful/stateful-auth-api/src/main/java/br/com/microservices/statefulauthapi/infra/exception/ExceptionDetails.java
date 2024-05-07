package br.com.microservices.statefulauthapi.infra.exception;

public record ExceptionDetails(int status, Object message) {
}
