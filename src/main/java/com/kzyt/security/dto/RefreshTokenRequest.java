package com.kzyt.security.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "Refresh token cannot be empty!")
        String refreshToken
) {
}
