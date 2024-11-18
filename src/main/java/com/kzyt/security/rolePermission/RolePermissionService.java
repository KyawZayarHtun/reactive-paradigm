package com.kzyt.security.rolePermission;

import com.kzyt.security.permission.PermissionService;
import com.kzyt.security.role.RoleService;
import com.kzyt.security.rolePermission.dto.AssignPermissions;
import com.kzyt.security.rolePermission.dto.RolePermissionResponse;
import com.kzyt.security.rolePermission.dto.RoleWithPermissions;
import com.kzyt.util.error.ObjectNotFoundError;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public record RolePermissionService(
        RolePermissionRepository rolePermissionRepository,
        RoleService roleService,
        PermissionService permissionService
) {

    public Flux<RolePermissionResponse> assignPermissionsToRole(String roleId, AssignPermissions assignPermissions) {

        return roleService.existsRoleById(roleId)
                .flatMapMany(exist -> {

                    if (!exist)
                        return Flux.error(new ObjectNotFoundError(roleId + " not found"));

                    List<RolePermission> rolePermissions = assignPermissions.getPermissionIds().stream()
                            .map(permissionId -> new RolePermission(roleId, permissionId))
                            .toList();
                    return rolePermissionRepository.deleteByRoleId(roleId)
                            .thenMany(rolePermissionRepository.saveAll(rolePermissions))
                            .map(RolePermissionResponse::from);
                });
    }

    public Mono<RoleWithPermissions> getPermissionsForRole(String roleId) {
        return roleService.existsRoleById(roleId)
                .flatMap(exist -> {

                    if (!exist)
                        return Mono.error(new ObjectNotFoundError(roleId + " not found"));

                    return rolePermissionRepository.findByRoleId(roleId)
                            .flatMap(rolePermission -> permissionService.getPermissionById(rolePermission.getPermissionId()))
                            .collect(Collectors.toSet())
                            .map(permissionResponses -> new RoleWithPermissions(roleId, permissionResponses));
                });
    }



}
