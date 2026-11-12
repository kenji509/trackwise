package com.csit321g2.bainabai.trackwise.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(int status, String message, Map<String, String> errors, LocalDateTime timestamp) {
}
