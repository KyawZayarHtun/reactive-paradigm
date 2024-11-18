package com.kzyt.security.user.dto;

import com.kzyt.security.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserResponse(
        String id,
        String name,
        String email,
        String phoneNumber,
        LocalDate birthDate,
        boolean locked,
        LocalDateTime createdTime,
        LocalDateTime updatedTime,
        String roleId
) {

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getPhoneNumber(), user.getBirthDate(),
                user.isLocked(), user.getCreatedTime(), user.getUpdatedTime(), user.getRoleId());
    }

}
