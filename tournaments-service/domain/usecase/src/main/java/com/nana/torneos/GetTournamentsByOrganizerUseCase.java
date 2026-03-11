package com.nana.torneos;

import com.nana.torneos.gateways.TournamentRepository;
import com.nana.torneos.tournaments.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class GetTournamentsByOrganizerUseCase {

    private final TournamentRepository repository;

    public Flux<Tournament> execute(Integer organizerId) {
        return repository.findByOrganizerId(organizerId);
    }
}