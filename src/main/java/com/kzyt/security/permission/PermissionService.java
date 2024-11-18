package com.kzyt.security.permission;

import com.kzyt.security.permission.dto.PermissionCreate;
import com.kzyt.security.permission.dto.PermissionResponse;
import com.kzyt.security.permission.dto.PermissionSearchCriteria;
import com.kzyt.security.permission.dto.PermissionUpdate;
import com.kzyt.util.Page;
import com.kzyt.util.error.ObjectNotFoundError;
import com.kzyt.util.helper.ReactiveMongoHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public record PermissionService(
        PermissionRepository permissionRepository,
        ReactiveMongoHelper reactiveMongoHelper
) {

    public Mono<Page<PermissionResponse>> getPermissionWithPage(PermissionSearchCriteria criteria) {
        return reactiveMongoHelper.getPagedResult(criteria, Permission.class, PermissionResponse::from);
    }

    public Mono<PermissionResponse> getPermissionById(String id) {
        return permissionRepository.findById(id)
                .switchIfEmpty(Mono.error(new ObjectNotFoundError(id + " not found")))
                .map(PermissionResponse::from);
    }

    public Mono<PermissionResponse> createPermission(PermissionCreate permissionCreate) {
        return permissionRepository.insert(new Permission(permissionCreate)).map(PermissionResponse::from);
    }

    public Mono<PermissionResponse> updatePermission(String id, PermissionUpdate update) {
        return permissionRepository.findById(id)
                .switchIfEmpty(Mono.error(new ObjectNotFoundError(id + " not found")))
                .flatMap(permission -> {
                    if (update.getName() != null && !update.getName().isBlank())
                        permission.setName(update.getName());
                    if (update.getDescription() != null && !update.getDescription().isBlank())
                        permission.setDescription(update.getDescription());
                    return permissionRepository.save(permission);
                }).map(PermissionResponse::from);
    }

    public Mono<Void> deletePermissionById(String id) {
        return permissionRepository.findById(id)
                .switchIfEmpty(Mono.error(new ObjectNotFoundError(id + " not found")))
                .flatMap(role -> permissionRepository.deleteById(id));
    }

}
