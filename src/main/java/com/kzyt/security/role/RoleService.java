package com.kzyt.security.role;

import com.kzyt.security.role.dto.RoleCreate;
import com.kzyt.security.role.dto.RoleNameUpdate;
import com.kzyt.security.role.dto.RoleResponse;
import com.kzyt.security.role.dto.RoleSearchCriteria;
import com.kzyt.util.Page;
import com.kzyt.util.error.ObjectNotFoundException;
import com.kzyt.util.helper.ReactiveMongoHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public record RoleService(
        RoleRepository roleRepository,
        ReactiveMongoHelper reactiveMongoHelper
) {
    public Mono<Page<RoleResponse>> getRoleWithPage(RoleSearchCriteria criteria) {
        return reactiveMongoHelper.getPagedResult(criteria, Role.class, RoleResponse::from);
    }

    public Mono<RoleResponse> getRoleById(String id) {
        return roleRepository.findById(id)
                .switchIfEmpty(Mono.error(new ObjectNotFoundException(id + " not found")))
                .map(RoleResponse::from);
    }

    public Mono<RoleResponse> createRole(RoleCreate role) {
        return roleRepository.insert(new Role(role)).map(RoleResponse::from);
    }

    public Mono<RoleResponse> updateRoleName(String id, RoleNameUpdate update) {
        return roleRepository.findById(id)
                .switchIfEmpty(Mono.error(new ObjectNotFoundException(id + " not found")))
                .flatMap(role -> {
                    if (update.getName() != null && !update.getName().isBlank())
                        role.setName(update.getName());
                    return roleRepository.save(role);
                }).map(RoleResponse::from);
    }

    public Mono<Void> deleteRoleById(String id) {
        return roleRepository.findById(id)
                .switchIfEmpty(Mono.error(new ObjectNotFoundException(id + " not found")))
                .flatMap(role -> roleRepository.deleteById(id));
    }

    public Mono<Boolean> existsRoleById(String id) {
        return roleRepository.existsById(id);
    }
}
