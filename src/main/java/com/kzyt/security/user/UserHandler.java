package com.kzyt.security.user;

import com.kzyt.security.user.dto.UserRegistration;
import com.kzyt.security.user.dto.UserResponse;
import com.kzyt.security.user.dto.UserSearchCriteria;
import com.kzyt.security.user.dto.UserStatusUpdate;
import com.kzyt.util.Page;
import com.kzyt.util.helper.BeanValidationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserService userService;
    private final BeanValidationHelper validationHelper;

    @PreAuthorize("hasAuthority('READ_USER')")
    public Mono<ServerResponse> getAll(ServerRequest req) {
        UserSearchCriteria criteria = UserSearchCriteria.from(req);
        Mono<Page<UserResponse>> users = userService.getUsersWithPage(criteria);
        return ServerResponse.ok().body(users, Page.class);
    }

    public Mono<ServerResponse> getUser(ServerRequest req) {
        return userService.getUserById(req.pathVariable("id"))
                .flatMap(user -> ServerResponse.ok().bodyValue(user));
    }

    @PreAuthorize("hasAuthority('WRITE_USER')")
    public Mono<ServerResponse> register(ServerRequest req) {
        return validationHelper.validateBody(req, UserRegistration.class)
                .flatMap(userService::register)
                .flatMap(registration -> ServerResponse.ok().bodyValue(registration));
    }

    public Mono<ServerResponse> patchUpdateStatusAndRole(ServerRequest req) {
        String id = req.pathVariable("id");
        return req.bodyToMono(UserStatusUpdate.class)
                .flatMap(userStatusUpdate -> userService.updateUserStatus(id, userStatusUpdate))
                .flatMap(userResponse -> ServerResponse.ok().bodyValue(userResponse));

    }
}
