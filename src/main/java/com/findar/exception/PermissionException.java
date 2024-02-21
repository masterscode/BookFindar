package com.findar.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class PermissionException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 2982216599071591482L;
    public PermissionException(String message) {
        super(message);
    }

}
