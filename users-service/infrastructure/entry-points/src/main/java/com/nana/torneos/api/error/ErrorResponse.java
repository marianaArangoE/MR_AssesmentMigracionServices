package com.nana.torneos.api.error;
public record ErrorResponse(
        String timestamp,
        int status,
        String error,
        String message,
        String path
) {}
