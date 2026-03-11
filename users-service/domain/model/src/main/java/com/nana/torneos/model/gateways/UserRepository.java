package com.nana.torneos.model.gateways;

import com.nana.torneos.model.users.User;
import reactor.core.publisher.Mono;
public interface UserRepository {
    Mono<User> save(User user);
    Mono<User> findById(Integer id);
    Mono<User> findByEmail (String email);
    Mono<Boolean> existsById(Integer id);
    Mono<Boolean> existsByEmail(String email);
    Mono <Boolean> existsByNickname (String nickname);
}