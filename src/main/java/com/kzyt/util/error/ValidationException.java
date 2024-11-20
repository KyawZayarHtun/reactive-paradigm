package com.kzyt.util.error;

import lombok.Getter;
import org.springframework.validation.BeanPropertyBindingResult;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ValidationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6228134918024357444L;

    private final Map<String, String> messages = new HashMap<>();

    public ValidationException(BeanPropertyBindingResult result) {
        for (var fe : result.getFieldErrors()) {
            messages.put(fe.getField(), fe.getDefaultMessage());
        }
    }
}
