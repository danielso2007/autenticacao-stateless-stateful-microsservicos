package br.com.microservices.statelessanyapi.infra.exception;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionGlobalHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handlerValidationException(ValidationException validationException) {
        var details = new ExceptionDetails(HttpStatus.BAD_REQUEST.value(), validationException.getMessage());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handlerAuthenticationException(AuthenticationException authenticationException) {
        var details = new ExceptionDetails(HttpStatus.UNAUTHORIZED.value(), authenticationException.getMessage());
        return new ResponseEntity<>(details, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        var details = new ExceptionDetails(HttpStatus.BAD_REQUEST.value(), errors);
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }
        
}
