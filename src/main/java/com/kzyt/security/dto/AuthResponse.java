package com.kzyt.security.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}
