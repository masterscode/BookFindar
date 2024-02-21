/*
 * *
 *  * Created by Kolawole Omirin
 *  * Copyright (c) 2023 . All rights reserved.
 *  * Last modified 11/15/23, 3:19 PM
 *
 */

package com.findar.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5067211343246617692L;

    public ResourceNotFoundException(String message) {
        super(message);
    }


}
