package com.nana.torneos.db;

import com.nana.torneos.gateways.TournamentRepository;
import com.nana.torneos.tournaments.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class R2DBCAdapter implements TournamentRepository {

    private final R2DBCTournamentRepository repository;

    @Override
    public Mono<Tournament> save(Tournament tournament) {

        TournamentData data = TournamentData.builder()
                .id(tournament.getId())
                .videoGameId(tournament.getVideoGameId())
                .name(tournament.getName())
                .startDate(tournament.getStartDate())
                .endDate(tournament.getEndDate())
                .itsFree(tournament.getIsFree() ? "T" : "F")
                .teamLimit(tournament.getTeamLimit())
                .viewLimit(tournament.getViewLimit())
                .platformId(tournament.getPlatformId())
                .organizerId(tournament.getOrganizerId())
                .categoryId(tournament.getCategoryId())
                .description(tournament.getDescription())
                .build();

        // Si NO hay id, es nuevo (INSERT)
        if (tournament.getId() == null) {
            return repository.save(data.markNew())
                    .map(this::toDomain);
        }

        // Si hay id: si existe -> UPDATE, si no existe -> INSERT
        return repository.existsById(tournament.getId())
                .flatMap(exists ->
                        repository.save(exists ? data.markNotNew() : data.markNew())
                )
                .map(this::toDomain);
    }

    @Override
    public Mono<Boolean> existsByExactData(
            String name,
            java.time.LocalDate startDate,
            java.time.LocalDate endDate,
            Integer videoGameId,
            Integer organizerId) {

        return repository.countExactTournament(
                        name.trim(),
                        startDate,
                        endDate,
                        videoGameId,
                        organizerId
                )
                .map(count -> count > 0);
    }

    @Override
    public Mono<Long> countFreeWith20ViewsByOrganizer(Integer organizerId) {
        return repository.countFreeWith20ViewsByOrganizer(organizerId);
    }

    private Tournament toDomain(TournamentData data) {
        return Tournament.builder()
                .id(data.getId())
                .videoGameId(data.getVideoGameId())
                .name(data.getName() == null ? null : data.getName().trim())
                .startDate(data.getStartDate())
                .endDate(data.getEndDate())
                .isFree(data.getItsFree() != null && data.getItsFree().trim().equals("T"))
                .teamLimit(data.getTeamLimit())
                .viewLimit(data.getViewLimit())
                .platformId(data.getPlatformId())
                .organizerId(data.getOrganizerId())
                .categoryId(data.getCategoryId())
                .description(data.getDescription() == null ? null : data.getDescription().trim())
                .build();
    }

    @Override
    public Flux<Tournament> findByOrganizerId(Integer organizerId) {
        return repository.findByOrganizerId(organizerId)
                .map(this::toDomain);
    }

    @Override
    public Mono<Tournament> findByIdAndOrganizerId(Integer tournamentId, Integer organizerId) {
        return repository.findByIdAndOrganizerId(tournamentId, organizerId)
                .map(this::toDomain);
    }
}