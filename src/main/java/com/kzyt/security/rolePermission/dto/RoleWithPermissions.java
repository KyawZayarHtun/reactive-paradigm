package com.kzyt.security.rolePermission.dto;

import com.kzyt.security.permission.dto.PermissionResponse;

import java.util.Set;

public record RoleWithPermissions(
        String roleId,
        Set<PermissionResponse> permissions
) {
}
