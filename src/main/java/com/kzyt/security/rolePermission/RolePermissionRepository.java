package com.kzyt.security.rolePermission;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RolePermissionRepository extends ReactiveMongoRepository<RolePermission, String> {

    Flux<RolePermission> findByRoleId(String roleId);
    Flux<RolePermission> findByPermissionId(String permissionId);
    Mono<Void> deleteByRoleId(String roleId);

}
