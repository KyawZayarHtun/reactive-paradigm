package com.kzyt.security;

import com.kzyt.security.dto.AuthRequest;
import com.kzyt.security.dto.AuthResponse;
import com.kzyt.security.dto.RefreshTokenRequest;
import com.kzyt.security.user.UserService;
import com.kzyt.util.helper.BeanValidationHelper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public record SecurityHandler(
        BeanValidationHelper validationHelper,
        SecurityService securityService,
        UserService userService,
        PasswordEncoder passwordEncoder,
        ReactiveAuthenticationManager authenticationManager
) {

    public Mono<ServerResponse> login(ServerRequest req) {
        return validationHelper.validateBody(req, AuthRequest.class)
                .flatMap(authRequest -> {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password());
                    return authenticationManager.authenticate(authenticationToken)
                            .flatMap(auth -> {
                                String jwtToken = securityService.generateJwtToken(auth);
                                String refreshToken = securityService.generateRefreshToken(auth);
                                return ServerResponse.ok().body(Mono.just(new AuthResponse(jwtToken, refreshToken)), AuthResponse.class);
                            });
                });
    }

    public Mono<ServerResponse> refreshToken(ServerRequest req) {
        return validationHelper.validateBody(req, RefreshTokenRequest.class)
                .flatMap(refreshTokenRequest -> securityService.validateToken(refreshTokenRequest.refreshToken())
                        .flatMap(email -> {
                            var token = UsernamePasswordAuthenticationToken.authenticated(email, null, null);
                            return authenticationManager.authenticate(token);
                        })
                        .flatMap(auth -> {
                            String jwtToken = securityService.generateJwtToken(auth);
                            String refreshToken = securityService.generateRefreshToken(auth);
                            return ServerResponse.ok().body(Mono.just(new AuthResponse(jwtToken, refreshToken)), AuthResponse.class);
                        }))
                .onErrorResume(e -> ServerResponse.status(HttpStatus.UNAUTHORIZED).bodyValue(e.getMessage()));

    }


}
