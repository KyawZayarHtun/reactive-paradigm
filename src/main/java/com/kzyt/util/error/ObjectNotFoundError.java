package com.kzyt.util.error;

import lombok.Getter;

import java.io.Serial;

@Getter
public class ObjectNotFoundError extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6228134918024357444L;

    public ObjectNotFoundError(String message) {
        super(message);
    }
}
