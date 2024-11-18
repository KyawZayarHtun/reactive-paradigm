package com.kzyt.security.rolePermission.dto;

import com.kzyt.security.rolePermission.RolePermission;

import java.time.LocalDateTime;

public record RolePermissionResponse(
        String id,
        String roleId,
        String permissionId,
        LocalDateTime createdTime,
        LocalDateTime updatedTime
) {

    public static RolePermissionResponse from(RolePermission rolePermission) {
        return new RolePermissionResponse(rolePermission.getId(), rolePermission.getRoleId(), rolePermission.getPermissionId(),
                rolePermission.getCreatedTime(), rolePermission.getUpdatedTime());
    }

}
