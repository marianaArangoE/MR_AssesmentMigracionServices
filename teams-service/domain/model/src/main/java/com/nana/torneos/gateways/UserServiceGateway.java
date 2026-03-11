package com.nana.torneos.gateways;

import reactor.core.publisher.Mono;

public interface UserServiceGateway {

    Mono<Boolean> userExists(Integer userId);

    Mono<Integer> getUserTeam(Integer userId);

    Mono<Void> assignUserToTeam(Integer userId, Integer teamId);

    Mono<Void> removeUserFromTeam(Integer userId);
}