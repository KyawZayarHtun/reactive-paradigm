package com.kzyt.router;

import com.kzyt.security.SecurityHandler;
import com.kzyt.security.permission.PermissionHandler;
import com.kzyt.security.role.RoleHandler;
import com.kzyt.security.rolePermission.RolePermissionHandler;
import com.kzyt.security.user.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration(proxyBeanMethods = false)
public class RouterConfig {

    RequestPredicate acceptType = accept(APPLICATION_JSON);

    @Bean
    public RouterFunction<ServerResponse> userRoutes(UserHandler userHandler) {
        return nest(path("/users"), route()
                .GET("", acceptType, userHandler::getAll)
                .GET("{id}", acceptType, userHandler::getUser)
                .POST("", acceptType, userHandler::register)
                .PATCH("{id}", acceptType, userHandler::patchUpdateStatusAndRole)
                .build());
    }

    @Bean
    public RouterFunction<ServerResponse> securityRoutes(SecurityHandler securityHandler) {
        return nest(path("/auth"), route()
                .POST("login", acceptType, securityHandler::login)
                .POST("refresh-token", acceptType, securityHandler::refreshToken)
                .build());
    }

    @Bean
    public RouterFunction<ServerResponse> roleRoutes(RoleHandler roleHandler, RolePermissionHandler rolePermissionHandler) {
        return nest(path("/roles"), route()
                .GET("", acceptType, roleHandler::getAllRoles)
                .GET("{id}", acceptType, roleHandler::getRoleById)
                .GET("{id}/permissions", acceptType, rolePermissionHandler::getPermissionsForRole)
                .POST("", acceptType, roleHandler::createRole)
                .POST("{id}/permissions", acceptType, rolePermissionHandler::assignPermissionsToRole)
                .PUT("{id}", acceptType, roleHandler::updateRole)
                .DELETE("{id}", acceptType, roleHandler::deleteRole)
                .build());
    }

    @Bean
    public RouterFunction<ServerResponse> permissionRoutes(PermissionHandler permissionHandler) {
        return nest(path("/permissions"), route()
                .GET("", acceptType, permissionHandler::getAllPermissions)
                .GET("{id}", acceptType, permissionHandler::getPermissionById)
                .POST("", acceptType, permissionHandler::createPermission)
                .PUT("{id}", acceptType, permissionHandler::updatePermission)
                .DELETE("{id}", acceptType, permissionHandler::deletePermission)
                .build());
    }

}
