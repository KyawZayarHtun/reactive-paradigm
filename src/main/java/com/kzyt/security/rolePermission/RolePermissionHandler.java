package com.kzyt.security.rolePermission;

import com.kzyt.security.rolePermission.dto.AssignPermissions;
import com.kzyt.security.rolePermission.dto.RolePermissionResponse;
import com.kzyt.util.helper.BeanValidationHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public record RolePermissionHandler(
        RolePermissionService rolePermissionService,
        BeanValidationHelper validationHelper
) {

    public Mono<ServerResponse> assignPermissionsToRole(ServerRequest req) {
        return req.bodyToMono(AssignPermissions.class)
                .flatMap(assignPermissions -> ServerResponse.ok().body(rolePermissionService.assignPermissionsToRole(req.pathVariable("id"), assignPermissions), RolePermissionResponse.class));
    }

    public Mono<ServerResponse> getPermissionsForRole(ServerRequest req) {
        return rolePermissionService.getPermissionsForRole(req.pathVariable("id"))
                .flatMap(roleWithPermissions -> ServerResponse.ok().bodyValue(roleWithPermissions));
    }

}
