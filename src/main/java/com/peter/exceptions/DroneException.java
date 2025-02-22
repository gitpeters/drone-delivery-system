package com.peter.exceptions;

import lombok.Getter;

@Getter
public class DroneException extends RuntimeException {
    private final int errorCode;
    public DroneException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
