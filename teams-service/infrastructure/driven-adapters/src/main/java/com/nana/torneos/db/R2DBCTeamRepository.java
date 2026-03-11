package com.nana.torneos.db;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface R2DBCTeamRepository extends ReactiveCrudRepository<TeamData, Integer> {

    @Query("""
        SELECT id_equipo, nombre, siglas, id_creador
        FROM equipos
        WHERE id_equipo = :id
    """)
    Mono<TeamData> findTeamById(@Param("id") Integer id);

    @Query("""
        SELECT COUNT(*) FROM equipos
        WHERE LOWER(nombre) = LOWER(:name)
           OR LOWER(siglas) = LOWER(:acronym)
    """)
    Mono<Long> countByNameOrAcronym(@Param("name") String name, @Param("acronym") String acronym);
}