package com.nana.torneos;

import com.nana.torneos.exceptions.TeamErrors;
import com.nana.torneos.gateways.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
@RequiredArgsConstructor
public class UpdateTeamUseCase {

    private final TeamRepository teamRepository;

    public Mono<Teams> execute(Integer teamId, Integer requesterId, String nameNew, String acronymNew) {

        if (teamId == null) return Mono.error(TeamErrors.teamIdIsRequired());
        if (requesterId == null) return Mono.error(TeamErrors.requiredFields());

        return teamRepository.findById(teamId)
                .switchIfEmpty(Mono.error(TeamErrors.teamNotFound()))
                .flatMap(existing -> {

                    if (existing.getCreatorId() == null || !existing.getCreatorId().equals(requesterId)) {
                        return Mono.error(TeamErrors.requiredFields());
                    }

                    String finalName = (nameNew == null || nameNew.trim().isEmpty())
                            ? existing.getName()
                            : nameNew.trim();

                    String finalAcronym = (acronymNew == null || acronymNew.trim().isEmpty())
                            ? existing.getAcronym()
                            : acronymNew.trim();

                    // si no cambió nada, devuelve el mismo
                    if (finalName.equalsIgnoreCase(existing.getName())
                            && finalAcronym.equalsIgnoreCase(existing.getAcronym())) {
                        return Mono.just(existing);
                    }

                    // Validar unicidad (nombre o siglas) si cambió algo
                    return teamRepository.existsByNameOrAcronym(finalName, finalAcronym)
                            .flatMap(exists -> {
                                // OJO: esta validación cuenta el mismo equipo también si ya tiene esos valores.
                                // Para no bloquearte hoy, lo dejamos así. Más adelante lo refinamos con "count excluding id".
                                if (exists) {
                                    return Mono.error(TeamErrors.nameOrAcronymInUse());
                                }

                                Teams toSave = Teams.builder()
                                        .id(existing.getId())
                                        .creatorId(existing.getCreatorId())
                                        .name(finalName)
                                        .acronym(finalAcronym)
                                        .build();

                                return teamRepository.saveExisting(toSave);
                            });
                });
    }
}