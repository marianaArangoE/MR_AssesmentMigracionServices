package com.nana.torneos;

import com.nana.torneos.gateways.TournamentRepository;
import com.nana.torneos.tournaments.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class UpdateTournamentUseCase {

    private final TournamentRepository repository;

    public Mono<Tournament> execute(Integer tournamentId, Integer organizerId, Tournament patch) {

        return repository.findByIdAndOrganizerId(tournamentId, organizerId)
                .switchIfEmpty(Mono.error(new IllegalStateException("Torneo no encontrado o no pertenece al usuario")))
                .flatMap(existing -> {
                    Tournament merged = merge(existing, patch);

                    validate(merged);

                    return repository.save(merged);
                });
    }

    private Tournament merge(Tournament existing, Tournament patch) {

        return Tournament.builder()
                .id(existing.getId())
                .organizerId(existing.getOrganizerId())

                .videoGameId(patch.getVideoGameId() != null ? patch.getVideoGameId() : existing.getVideoGameId())
                .name(patch.getName() != null ? patch.getName().trim() : existing.getName())
                .startDate(patch.getStartDate() != null ? patch.getStartDate() : existing.getStartDate())
                .endDate(patch.getEndDate() != null ? patch.getEndDate() : existing.getEndDate())
                .isFree(patch.getIsFree() != null ? patch.getIsFree() : existing.getIsFree())
                .teamLimit(patch.getTeamLimit() != null ? patch.getTeamLimit() : existing.getTeamLimit())
                .viewLimit(patch.getViewLimit() != null ? patch.getViewLimit() : existing.getViewLimit())
                .platformId(patch.getPlatformId() != null ? patch.getPlatformId() : existing.getPlatformId())
                .categoryId(patch.getCategoryId() != null ? patch.getCategoryId() : existing.getCategoryId())
                .description(patch.getDescription() != null ? patch.getDescription().trim() : existing.getDescription())
                .build();
    }

    private void validate(Tournament t) {
        LocalDate today = LocalDate.now();

        if (t.getStartDate() != null && t.getStartDate().isBefore(today)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser menor a la fecha actual");
        }

        if (t.getStartDate() != null && t.getEndDate() != null && t.getStartDate().isAfter(t.getEndDate())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser mayor a la fecha de fin");
        }

        if (t.getTeamLimit() != null && (t.getTeamLimit() % 2 != 0)) {
            throw new IllegalArgumentException("La cantidad de equipos debe ser un número par");
        }

        if (t.getViewLimit() != null && t.getViewLimit() <= 0) {
            throw new IllegalArgumentException("El límite de views debe ser mayor a cero");
        }
    }
}