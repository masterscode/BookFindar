
package com.findar.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class InternalOpsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5067211343246617692L;

    public InternalOpsException(String message) {
        super(message);
    }


}
