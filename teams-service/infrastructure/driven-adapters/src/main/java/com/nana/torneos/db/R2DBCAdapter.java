package com.nana.torneos.db;

import com.nana.torneos.gateways.TeamRepository;
import com.nana.torneos.Teams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class R2DBCAdapter implements TeamRepository {

    private final R2DBCTeamRepository repository;

    @Override
    public Flux<Teams> findAll() {
        return repository.findAll()
                .map(this::toDomain);
    }

    @Override
    public Mono<Teams> findById(Integer id) {
        return repository.findTeamById(id)
                .map(this::toDomain);
    }

    @Override
    public Mono<Boolean> existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public Mono<Boolean> existsByNameOrAcronym(String name, String acronym) {
        return repository.countByNameOrAcronym(
                name == null ? null : name.trim(),
                acronym == null ? null : acronym.trim()
        ).map(count -> count > 0);
    }

    @Override
    public Mono<Teams> saveNew(Teams team) {
        TeamData data = toData(team).markNew();
        return repository.save(data).map(this::toDomain);
    }

    @Override
    public Mono<Teams> saveExisting(Teams team) {
        TeamData data = toData(team).markNotNew();
        return repository.save(data).map(this::toDomain);
    }

    private TeamData toData(Teams team) {
        return TeamData.builder()
                .id(team.getId())
                .name(team.getName())
                .acronym(team.getAcronym())
                .creatorId(team.getCreatorId())
                .build();
    }

    private Teams toDomain(TeamData data) {
        return Teams.builder()
                .id(data.getId())
                .name(data.getName())
                .acronym(data.getAcronym())
                .creatorId(data.getCreatorId())
                .build();
    }
}
