package com.nana.torneos;

import com.nana.torneos.dtos.CreateTournamentRequest;
import com.nana.torneos.dtos.UpdateTournamentRequest;
import com.nana.torneos.security.JwtService;
import com.nana.torneos.tournaments.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TournamentHandler {

    private final CreateTournamentUseCase createTournamentUseCase;
    private final GetTournamentsByOrganizerUseCase getUseCase;
    private final JwtService jwtService;
    private final GetTournamentByIdUseCase getTournamentByIdUseCase;
    private final UpdateTournamentUseCase updateUseCase;

    public Mono<Tournament> createTournament(
            String authHeader,
            CreateTournamentRequest request) {

        Integer organizerId = extractUserId(authHeader);

        Tournament tournament = Tournament.builder()
                .videoGameId(request.getVideoGameId())
                .name(request.getName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .isFree(request.getFree())
                .teamLimit(request.getTeamLimit())
                .viewLimit(request.getViewLimit())
                .platformId(request.getPlatformId())
                .categoryId(request.getCategoryId())
                .description(request.getDescription())
                .organizerId(organizerId)
                .build();

        return createTournamentUseCase.execute(tournament);
    }

    public Flux<Tournament> getTournaments(String authHeader) {

        Integer userId = extractUserId(authHeader);

        return getUseCase.execute(userId);
    }
    private Integer extractUserId(String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Token requerido");
        }

        try {
            String token = authHeader.substring(7);
            return jwtService.extractUserId(token);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Token inválido o expirado");
        }

    }
    public Mono<Tournament> getTournamentById(
            String authHeader,
            Integer id) {

        Integer organizerId = extractUserId(authHeader);

        return getTournamentByIdUseCase.execute(id, organizerId);
    }

    public Mono<Tournament> updateTournament(
            String authHeader,
            Integer tournamentId,
            UpdateTournamentRequest request
    ) {
        Integer organizerId = extractUserId(authHeader);

        Tournament patch = Tournament.builder()
                .name(request.getName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .isFree(request.getFree())
                .teamLimit(request.getTeamLimit())
                .viewLimit(request.getViewLimit())
                .platformId(request.getPlatformId())
                .categoryId(request.getCategoryId())
                .description(request.getDescription())
                .build();

        return updateUseCase.execute(tournamentId, organizerId, patch);
    }
}