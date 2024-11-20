package com.kzyt.util.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
