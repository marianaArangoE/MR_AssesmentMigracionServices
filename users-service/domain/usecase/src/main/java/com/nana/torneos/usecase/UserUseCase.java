package com.nana.torneos.usecase;

import com.nana.torneos.model.exceptions.UserErrors;
import com.nana.torneos.model.security.PasswordEncryptor;
import com.nana.torneos.model.gateways.UserRepository;
import com.nana.torneos.model.users.User;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;

    public Mono<User> findById(Integer id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(UserErrors.userNotFound()));
    }

    public Mono<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(UserErrors.userNotFound()));
    }
}