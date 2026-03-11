package com.nana.torneos.db;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface R2DBCTournamentRepository extends ReactiveCrudRepository <TournamentData, Integer> {
    @Query("""
        SELECT COUNT(*)
        FROM torneos
        WHERE organizador = :organizerId
          AND its_free = 'T'
          AND limite_views = 20
    """
    )
    Mono<Long> countFreeWith20ViewsByOrganizer(Integer organizerId);
    @Query("""
        SELECT COUNT(*)
        FROM torneos
        WHERE TRIM(nombre) = :name
          AND fecha_inicio = :startDate
          AND fecha_fin = :endDate
          AND video_juegos_id = :videoGameId
          AND organizador = :organizerId
    """)
    Mono<Long> countExactTournament(
            String name,
            LocalDate startDate,
            LocalDate endDate,
            Integer videoGameId,
            Integer organizerId
    );

    Flux<TournamentData> findByOrganizerId(Integer organizerId);
    Mono<TournamentData> findByIdAndOrganizerId(Integer id, Integer organizerId);
}
