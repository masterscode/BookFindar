package com.findar.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class DuplicateEntityException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 7101434904253494890L;


    public DuplicateEntityException(String s){
        super(s);
    }

}
