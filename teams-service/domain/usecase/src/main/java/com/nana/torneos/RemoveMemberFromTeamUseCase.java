package com.nana.torneos;

import com.nana.torneos.exceptions.TeamErrors;
import com.nana.torneos.gateways.TeamRepository;
import com.nana.torneos.gateways.UserServiceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RemoveMemberFromTeamUseCase {

    private final TeamRepository teamRepository;
    private final UserServiceGateway userServiceGateway;

    public Mono<String> execute(Integer teamId, Integer userId, Integer requesterId) {
        if (teamId == null) return Mono.error(TeamErrors.teamIdIsRequired());
        if (userId == null) return Mono.error(TeamErrors.userIdRequired());
        if (requesterId == null) return Mono.error(TeamErrors.requesterIdRequired());

        return teamRepository.findById(teamId)
                .switchIfEmpty(Mono.error(TeamErrors.teamNotFound()))
                .flatMap(team -> {
                    if (team.getCreatorId() == null || !team.getCreatorId().equals(requesterId)) {
                        return Mono.error(TeamErrors.forbidden());
                    }

                    if (userId.equals(requesterId)) {
                        return Mono.error(TeamErrors.ownerCannotRemoveSelf());
                    }

                    return userServiceGateway.userExists(userId)
                            .flatMap(exists -> {
                                if (!exists) {
                                    return Mono.error(TeamErrors.userNotFound());
                                }

                                return userServiceGateway.getUserTeam(userId)
                                        .flatMap(currentTeamId -> {
                                            if (currentTeamId == null || !currentTeamId.equals(teamId)) {
                                                return Mono.error(TeamErrors.userNotInTeam());
                                            }

                                            return userServiceGateway.removeUserFromTeam(userId)
                                                    .thenReturn("Usuario eliminado del equipo correctamente");
                                        });
                            });
                });
    }
}