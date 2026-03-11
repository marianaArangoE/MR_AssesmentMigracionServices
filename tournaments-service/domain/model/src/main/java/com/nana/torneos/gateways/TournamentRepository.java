package com.nana.torneos.gateways;

import com.nana.torneos.tournaments.Tournament;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface TournamentRepository {

 Mono <Tournament> save (Tournament tournament);
     Mono<Boolean> existsByExactData(
             String name,
             LocalDate startDate,
             LocalDate endDate,
             Integer videoGameId,
             Integer organizerId
    );

     Mono<Long> countFreeWith20ViewsByOrganizer(Integer organizerId);
     Flux<Tournament> findByOrganizerId(Integer organizerId);

    Mono<Tournament> findByIdAndOrganizerId(Integer tournamentId, Integer organizerId);

}
