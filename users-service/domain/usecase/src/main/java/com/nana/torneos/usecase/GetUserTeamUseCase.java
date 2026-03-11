package com.nana.torneos.usecase;

import com.nana.torneos.model.exceptions.UserErrors;
import com.nana.torneos.model.gateways.UserRepository;
import com.nana.torneos.model.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GetUserTeamUseCase {

    private final UserRepository userRepository;

    public Mono<User> execute(Integer userId) {
        if (userId == null) {
            return Mono.error(UserErrors.idIsRequired());
        }

        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(UserErrors.userNotFound()));
    }
}