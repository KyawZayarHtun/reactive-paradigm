package com.kzyt.security.role.dto;

import com.kzyt.security.role.Role;

import java.time.LocalDateTime;

public record RoleResponse(
        String id,
        String name,
        LocalDateTime createdTime,
        LocalDateTime updatedTime
) {

    public static RoleResponse from(Role role) {
        return new RoleResponse(role.getId(), role.getName(), role.getCreatedTime(), role.getUpdatedTime());
    }

}
