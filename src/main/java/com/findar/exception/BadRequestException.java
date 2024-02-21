package com.findar.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class BadRequestException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 8589601677893112777L;

    public BadRequestException(String message) {
        super(message);
    }


}
