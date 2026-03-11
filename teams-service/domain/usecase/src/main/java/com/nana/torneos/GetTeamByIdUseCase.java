package com.nana.torneos;

import com.nana.torneos.exceptions.TeamErrors;
import com.nana.torneos.gateways.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
@RequiredArgsConstructor
public class GetTeamByIdUseCase {

    private final TeamRepository teamRepository;

    public Mono<Teams> execute(Integer teamId) {
        if (teamId == null) return Mono.error(TeamErrors.teamIdIsRequired());

        return teamRepository.findById(teamId)
                .switchIfEmpty(Mono.error(TeamErrors.teamNotFound()));
    }
}