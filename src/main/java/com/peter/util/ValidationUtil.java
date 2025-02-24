package com.peter.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

public class ValidationUtil {
    public static <T extends Enum<T>> ResponseEntity<?> validateEnum(String value, Class<T> enumClass, String fieldName) {
        try {
            Enum.valueOf(enumClass, value);
            return null; // Valid
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "timestamp", LocalDateTime.now().toString(),
                    "status", 400,
                    "error", "Validation Error",
                    "message", "Invalid " + fieldName + ". Accepted values are: " +
                            Arrays.toString(enumClass.getEnumConstants()),
                    "path", ((ServletRequestAttributes) RequestContextHolder
                            .currentRequestAttributes()).getRequest().getRequestURI()
            ));
        }
    }
}
