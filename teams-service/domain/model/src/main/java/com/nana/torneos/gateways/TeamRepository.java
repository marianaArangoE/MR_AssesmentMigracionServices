package com.nana.torneos.gateways;

import com.nana.torneos.Teams;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TeamRepository {
    Flux<Teams> findAll();
    Mono<Teams> findById(Integer id);

    Mono<Boolean> existsById(Integer id);

    Mono<Boolean> existsByNameOrAcronym(String name, String acronym);

    Mono<Teams> saveNew(Teams team);
    Mono<Teams> saveExisting(Teams team);
}