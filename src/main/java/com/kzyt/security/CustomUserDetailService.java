package com.kzyt.security;

import com.kzyt.security.rolePermission.RolePermissionService;
import com.kzyt.security.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements ReactiveUserDetailsService {

    private final UserService userService;
    private final RolePermissionService rolePermissionService;

    @Override
    public Mono<UserDetails> findByUsername(String email) {
        return userService.getUserByEmail(email)
                .flatMap(user -> rolePermissionService.getPermissionsNamesForRole(user.getRoleId())
                        .collectList()
                        .map(permission -> User.builder()
                                .username(email)
                                .password(user.getPassword())
                                .accountLocked(user.isLocked())
                                .authorities(permission.toArray(new String[0]))
                                .build()))
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found!")));
    }

}
