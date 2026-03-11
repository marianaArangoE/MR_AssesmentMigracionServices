package com.nana.torneos;
import com.nana.torneos.config.clientResponses.UserExistsResponse;
import com.nana.torneos.config.clientResponses.UserTeamResponse;
import com.nana.torneos.gateways.UserServiceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UsersClient implements UserServiceGateway {

    private final WebClient usersWebClient;

    public Mono<Boolean> userExists(Integer userId) {
        return usersWebClient.get()
                .uri("/{id}/exists", userId)
                .retrieve()
                .bodyToMono(UserExistsResponse.class)
                .map(UserExistsResponse::isExists);
    }

    public Mono<Integer> getUserTeam(Integer userId) {
        return usersWebClient.get()
                .uri("/{id}/team", userId)
                .retrieve()
                .bodyToMono(UserTeamResponse.class)
                .map(UserTeamResponse::getTeamId);
    }

    public Mono<Void> assignUserToTeam(Integer userId, Integer teamId) {
        return usersWebClient.put()
                .uri("/{id}/team", userId)
                .bodyValue(Map.of("team_id", teamId))
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Void> removeUserFromTeam(Integer userId) {
        return usersWebClient.delete()
                .uri("/{id}/team", userId)
                .retrieve()
                .bodyToMono(Void.class);
    }
}