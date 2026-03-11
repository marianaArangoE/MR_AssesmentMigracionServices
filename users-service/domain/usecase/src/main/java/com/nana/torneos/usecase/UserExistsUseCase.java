package com.nana.torneos.usecase;

import com.nana.torneos.model.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserExistsUseCase {

    private final UserRepository userRepository;

    public Mono<Boolean> execute(Integer userId) {
        return userRepository.existsById(userId);
    }
}