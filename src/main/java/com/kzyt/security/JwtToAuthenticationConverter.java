package com.kzyt.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import reactor.core.publisher.Mono;

import java.util.Collection;

public class JwtToAuthenticationConverter implements Converter<Jwt, Mono<? extends AbstractAuthenticationToken>> {

    private final JwtGrantedAuthoritiesConverter authoritiesConverter;

    public JwtToAuthenticationConverter() {
        this.authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        this.authoritiesConverter.setAuthoritiesClaimName("permissions"); // Adjust based on JWT claim
        this.authoritiesConverter.setAuthorityPrefix(""); // Prefix for authorities
    }

    @Override
    public Mono<? extends AbstractAuthenticationToken> convert(Jwt source) {
        // Convert the JWT to Authentication object
        Collection<GrantedAuthority> authorities = authoritiesConverter.convert(source);
        String subject = source.getSubject(); // This is typically the username or userId from the JWT

        // Return a UsernamePasswordAuthenticationToken
        return Mono.just(new UsernamePasswordAuthenticationToken(subject, null, authorities));
    }
}
