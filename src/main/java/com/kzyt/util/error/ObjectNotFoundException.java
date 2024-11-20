package com.kzyt.util.error;

import lombok.Getter;

import java.io.Serial;

@Getter
public class ObjectNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6228134918024357444L;

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
