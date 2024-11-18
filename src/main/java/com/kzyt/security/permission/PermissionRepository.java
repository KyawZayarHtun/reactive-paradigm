package com.kzyt.security.permission;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PermissionRepository extends ReactiveMongoRepository<Permission, String> {
}
