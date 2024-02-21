package com.findar.common;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse <T> {
    private String message;
    private Object error;
    private T data;
}
