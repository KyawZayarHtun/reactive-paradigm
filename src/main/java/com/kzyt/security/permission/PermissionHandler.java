package com.kzyt.security.permission;

import com.kzyt.security.permission.dto.PermissionCreate;
import com.kzyt.security.permission.dto.PermissionResponse;
import com.kzyt.security.permission.dto.PermissionSearchCriteria;
import com.kzyt.security.permission.dto.PermissionUpdate;
import com.kzyt.util.Page;
import com.kzyt.util.helper.BeanValidationHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public record PermissionHandler(
        PermissionService permissionService,
        BeanValidationHelper validationHelper
) {

    public Mono<ServerResponse> getAllPermissions(ServerRequest req) {
        PermissionSearchCriteria criteria = PermissionSearchCriteria.from(req);
        Mono<Page<PermissionResponse>> permissions = permissionService.getPermissionWithPage(criteria);
        return ServerResponse.ok().body(permissions, Page.class);
    }

    public Mono<ServerResponse> getPermissionById(ServerRequest req) {
        return permissionService.getPermissionById(req.pathVariable("id"))
                .flatMap(permissionResponse -> ServerResponse.ok().bodyValue(permissionResponse));
    }

    public Mono<ServerResponse> createPermission(ServerRequest req) {
        return validationHelper.validateBody(req, PermissionCreate.class)
                .flatMap(permissionService::createPermission)
                .flatMap(permissionResponse -> ServerResponse.ok().bodyValue(permissionResponse));
    }

    public Mono<ServerResponse> updatePermission(ServerRequest req) {
        String id = req.pathVariable("id");
        return req.bodyToMono(PermissionUpdate.class)
                .flatMap(update -> permissionService.updatePermission(id, update))
                .flatMap(permissionResponse -> ServerResponse.ok().bodyValue(permissionResponse));
    }

    public Mono<ServerResponse> deletePermission(ServerRequest req) {
        String id = req.pathVariable("id");
        return permissionService.deletePermissionById(id)
                .then(ServerResponse.noContent().build());
    }

}
