package com.kzyt.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public record SecurityService(
        JwtEncoder jwtEncoder,
        ReactiveJwtDecoder reactiveJwtDecoder
) {

    public String generateJwtToken(Authentication authentication) {
        JwtClaimsSet claims = getJwtClaimsSet(authentication, 3600);
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateRefreshToken(Authentication authentication) {
        JwtClaimsSet claims = getJwtClaimsSet(authentication, 604800);
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private List<String> getPermissions(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    private JwtClaimsSet getJwtClaimsSet(Authentication authentication, int sec) {
        var now = Instant.now();
        return JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(sec, ChronoUnit.SECONDS))
                .subject(authentication.getName())
                .claim("permissions", getPermissions(authentication))
                .build();
    }

    public Mono<String> validateToken(String refreshToken) {
        return reactiveJwtDecoder.decode(refreshToken)
                .flatMap(jwt -> {

                    String email = jwt.getSubject();
                    if (email == null || email.isBlank()) {
                        return Mono.error(new RuntimeException("Invalid token: missing userId"));
                    }

                    return Mono.just(email); // Return the userId
                });
    }

}
