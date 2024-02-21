package com.findar.exception;

import java.io.Serial;

public class SpringSecurityException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 3150456347060415248L;

    public SpringSecurityException(String message) {
        super(message);
    }
}
