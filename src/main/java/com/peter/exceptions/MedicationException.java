package com.peter.exceptions;

import lombok.Getter;

@Getter
public class MedicationException extends RuntimeException {
    private final int errorCode;
    public MedicationException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
