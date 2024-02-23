package com.vladkostromin.awss3app.security;

import com.vladkostromin.awss3app.enums.UserStatus;
import com.vladkostromin.awss3app.exception.UnauthorizedException;
import com.vladkostromin.awss3app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final UserService userService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        return userService.getUserById(principal.getId())
                .filter(user -> user.getStatus().equals(UserStatus.ACTIVE))
                .switchIfEmpty(Mono.error(new UnauthorizedException("User inactive")))
                .map(user -> authentication);
    }
}
