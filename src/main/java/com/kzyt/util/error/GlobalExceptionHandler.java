package com.kzyt.util.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleValidationException(ValidationException ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessages()));
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public Mono<ResponseEntity<String>> handleObjectNotFoundException(ObjectNotFoundException ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public Mono<ResponseEntity<String>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public Mono<ResponseEntity<String>> handleBadCredentialsException(BadCredentialsException ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ex.getMessage()));
    }

}
