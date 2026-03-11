package com.nana.torneos.error;

public record ErrorResponse(
        String timestamp,
        int status,
        String error,
        String message,
        String path
) {}
