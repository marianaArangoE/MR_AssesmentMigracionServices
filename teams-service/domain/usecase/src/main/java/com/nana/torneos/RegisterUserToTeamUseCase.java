package com.nana.torneos;

import com.nana.torneos.exceptions.TeamErrors;
import com.nana.torneos.gateways.TeamRepository;
import com.nana.torneos.gateways.UserServiceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RegisterUserToTeamUseCase {

    private final TeamRepository teamRepository;
    private final UserServiceGateway userServiceGateway;

    public Mono<Teams> execute(Integer teamId, Integer userId) {
        if (teamId == null) return Mono.error(TeamErrors.teamIdIsRequired());
        if (userId == null) return Mono.error(TeamErrors.userIdRequired());

        return teamRepository.findById(teamId)
                .switchIfEmpty(Mono.error(TeamErrors.teamNotFound()))
                .flatMap(team -> {
                    if (team.getCreatorId() != null && team.getCreatorId().equals(userId)) {
                        return Mono.error(TeamErrors.userIsOwnerNoNeedToJoin());
                    }

                    return userServiceGateway.userExists(userId)
                            .flatMap(exists -> {
                                if (!exists) {
                                    return Mono.error(TeamErrors.userNotFound());
                                }

                                return userServiceGateway.getUserTeam(userId)
                                        .flatMap(currentTeamId -> {
                                            if (currentTeamId != null) {
                                                return Mono.error(TeamErrors.userAlreadyInTeam());
                                            }

                                            return userServiceGateway.assignUserToTeam(userId, teamId)
                                                    .thenReturn(team);
                                        });
                            });
                });
    }
}