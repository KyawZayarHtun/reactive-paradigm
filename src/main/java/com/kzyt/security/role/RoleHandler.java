package com.kzyt.security.role;

import com.kzyt.security.role.dto.RoleCreate;
import com.kzyt.security.role.dto.RoleNameUpdate;
import com.kzyt.security.role.dto.RoleResponse;
import com.kzyt.security.role.dto.RoleSearchCriteria;
import com.kzyt.util.Page;
import com.kzyt.util.helper.BeanValidationHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public record RoleHandler(
        RoleService roleService,
        BeanValidationHelper validationHelper
) {

    public Mono<ServerResponse> getAllRoles(ServerRequest req) {
        RoleSearchCriteria criteria = RoleSearchCriteria.from(req);
        Mono<Page<RoleResponse>> roles = roleService.getRoleWithPage(criteria);
        return ServerResponse.ok().body(roles, Page.class);
    }

    public Mono<ServerResponse> getRoleById(ServerRequest req) {
        return roleService.getRoleById(req.pathVariable("id"))
                .flatMap(roleResponse -> ServerResponse.ok().bodyValue(roleResponse));
    }

    public Mono<ServerResponse> createRole(ServerRequest req) {
        return validationHelper.validateBody(req, RoleCreate.class)
                .flatMap(roleService::createRole)
                .flatMap(roleResponse -> ServerResponse.ok().bodyValue(roleResponse));
    }

    public Mono<ServerResponse> updateRole(ServerRequest req) {
        String id = req.pathVariable("id");
        return req.bodyToMono(RoleNameUpdate.class)
                .flatMap(update -> roleService.updateRoleName(id, update))
                .flatMap(roleResponse -> ServerResponse.ok().bodyValue(roleResponse));
    }

    public Mono<ServerResponse> deleteRole(ServerRequest req) {
        String id = req.pathVariable("id");
        return roleService.deleteRoleById(id)
                .then(ServerResponse.noContent().build());
    }


}
