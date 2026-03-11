package com.nana.torneos.usecase;

import com.nana.torneos.model.exceptions.UserErrors;
import com.nana.torneos.model.gateways.UserRepository;
import com.nana.torneos.model.security.PasswordEncryptor;
import com.nana.torneos.model.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncryptor passwordEncryptor;

    public Mono<User> execute(String email, String password) {

        if (email == null || password == null) {
            return Mono.error(UserErrors.invalidCredentials());
        }

        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(UserErrors.invalidCredentials()))
                .flatMap(user -> {

                    if (!passwordEncryptor.matches(password, user.getPassword().trim())) {
                        return Mono.error(UserErrors.invalidCredentials());
                    }

                    return Mono.just(user);
                });
    }
}