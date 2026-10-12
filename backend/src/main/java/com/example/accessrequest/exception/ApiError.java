package com.example.accessrequest.exception;

import java.time.LocalDateTime;
import java.util.Map;

/** Uniform error body returned by the API. */
public record ApiError(int status, String message, Map<String, String> fieldErrors, LocalDateTime timestamp) {
}
