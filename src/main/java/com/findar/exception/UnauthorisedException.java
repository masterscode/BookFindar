package com.findar.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class UnauthorisedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 2982216599071591482L;

    public UnauthorisedException(String message) {
        super(message);
    }


}
