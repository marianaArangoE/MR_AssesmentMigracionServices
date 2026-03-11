package com.nana.torneos;


import com.nana.torneos.gateways.TournamentRepository;
import com.nana.torneos.tournaments.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GetTournamentByIdUseCase {

    private final TournamentRepository repository;

    public Mono<Tournament> execute(Integer tournamentId, Integer organizerId) {

        return repository.findByIdAndOrganizerId(tournamentId, organizerId)
                .switchIfEmpty(
                        Mono.<Tournament>error(new IllegalStateException(
                                "Torneo no encontrado o no pertenece al usuario"))
                );
    }
}