package com.nana.torneos.adapters.db;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.List;

public interface R2DBCUserRepository extends ReactiveCrudRepository<UserData, Integer> {
    Mono<Boolean> existsByEmail(String email);
    Mono<UserData> findByEmail(String email);
    Mono<Boolean> existsByNickname(String nickname);

}