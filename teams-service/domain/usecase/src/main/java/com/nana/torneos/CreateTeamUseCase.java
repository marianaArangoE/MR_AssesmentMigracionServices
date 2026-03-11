package com.nana.torneos;

import com.nana.torneos.exceptions.TeamErrors;
import com.nana.torneos.gateways.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
@Component
@RequiredArgsConstructor
public class CreateTeamUseCase {

    private final TeamRepository teamRepository;
    private static final SecureRandom RANDOM = new SecureRandom();

    public Mono<Teams> execute(Teams request) {
        if (request == null) return Mono.error(TeamErrors.requiredFields());

        if (request.getName() == null || request.getName().trim().isEmpty()
                || request.getAcronym() == null || request.getAcronym().trim().isEmpty()
                || request.getCreatorId() == null) {
            return Mono.error(TeamErrors.requiredFields());
        }

        String name = request.getName().trim();
        String acronym = request.getAcronym().trim();

        return teamRepository.existsByNameOrAcronym(name, acronym)
                .flatMap(exists -> {
                    if (exists) return Mono.error(TeamErrors.nameOrAcronymInUse());

                    int idEquipo = 100000 + RANDOM.nextInt(900000);

                    Teams teamToSave = Teams.builder()
                            .id(idEquipo)
                            .name(name)
                            .acronym(acronym)
                            .creatorId(request.getCreatorId())
                            .build();

                    return teamRepository.saveNew(teamToSave);
                });
    }
}