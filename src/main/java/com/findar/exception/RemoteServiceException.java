package com.findar.exception;

import java.io.Serial;
import java.io.Serializable;

public class RemoteServiceException extends RuntimeException implements Serializable {
    @Serial
    private static final long serialVersionUID = -8224263069678879999L;

    public RemoteServiceException(String message) {
        super(message);
    }
}
