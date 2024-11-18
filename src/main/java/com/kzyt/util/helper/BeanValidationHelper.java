package com.kzyt.util.helper;

import com.kzyt.util.error.ValidationError;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@Service
public record BeanValidationHelper(
        Validator validator
) {

    public <T> Mono<T> validateBody(ServerRequest req, Class<T> bodyClass) {
        return req.bodyToMono(bodyClass)
                .flatMap(body -> {
                    BeanPropertyBindingResult errors = new BeanPropertyBindingResult(body, bodyClass.getName());
                    validator.validate(body, errors);
                    if (errors.hasErrors()) {
                        return Mono.error(new ValidationError(errors));
                    }
                    return Mono.just(body);
                })
                .switchIfEmpty(Mono.error(new RuntimeException()));
    }

}
