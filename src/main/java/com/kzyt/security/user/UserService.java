package com.kzyt.security.user;

import com.kzyt.security.user.dto.UserRegistration;
import com.kzyt.security.user.dto.UserResponse;
import com.kzyt.security.user.dto.UserSearchCriteria;
import com.kzyt.security.user.dto.UserStatusUpdate;
import com.kzyt.util.Page;
import com.kzyt.util.error.ObjectNotFoundError;
import com.kzyt.util.helper.ReactiveMongoHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public record UserService(
        UserRepository userRepository,
        ReactiveMongoHelper reactiveMongoHelper
) {

    public Mono<Page<UserResponse>> getUsersWithPage(UserSearchCriteria criteria) {
        return reactiveMongoHelper.getPagedResult(criteria, User.class, UserResponse::from);
    }

    public Mono<UserResponse> getUserById(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ObjectNotFoundError(id + " not found")))
                .map(UserResponse::from);
    }

    public Mono<UserResponse> register(UserRegistration registration) {
        return userRepository.insert(new User(registration)).map(UserResponse::from);
    }

    public Mono<UserResponse> updateUserStatus(String id, UserStatusUpdate userStatusUpdate) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ObjectNotFoundError(id + " not found")))
                .flatMap(user -> {
                    if (userStatusUpdate.getRoleId() != null && !userStatusUpdate.getRoleId().isBlank())
                        user.setRoleId(userStatusUpdate.getRoleId());
                    if (userStatusUpdate.getLocked() != null)
                        user.setLocked(userStatusUpdate.getLocked());
                    return userRepository.save(user);
                }).map(UserResponse::from);
    }
}
