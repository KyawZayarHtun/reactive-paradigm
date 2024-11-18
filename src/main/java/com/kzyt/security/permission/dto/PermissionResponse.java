package com.kzyt.security.permission.dto;

import com.kzyt.security.permission.Permission;

import java.time.LocalDateTime;

public record PermissionResponse(
        String id,
        String name,
        String description,
        LocalDateTime createdTime,
        LocalDateTime updatedTime
) {

    public static PermissionResponse from(Permission permission) {
        return new PermissionResponse(permission.getId(), permission.getName(), permission.getDescription(), permission.getCreatedTime(), permission.getUpdatedTime());
    }

}
