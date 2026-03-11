package com.nana.torneos;
import com.nana.torneos.dtos.CreateTournamentRequest;
import com.nana.torneos.dtos.UpdateTournamentRequest;
import com.nana.torneos.tournaments.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
        import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/torneos")
@RequiredArgsConstructor
public class RestRouter {

    private final TournamentHandler handler;

    @PostMapping
    public Mono<Tournament> create(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CreateTournamentRequest request
    ) {
        return handler.createTournament(authHeader, request);
    }

    @GetMapping
    public Flux<Tournament> getAll(
            @RequestHeader("Authorization") String authHeader
    ) {
        return handler.getTournaments(authHeader);
    }

    @GetMapping("/{id}")
    public Mono<Tournament> getById(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("id") Integer id
    ) {
        return handler.getTournamentById(authHeader, id);
    }
    @PutMapping("/{id}")
    public Mono<Tournament> update(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("id") Integer id,
            @RequestBody UpdateTournamentRequest request
    ) {
        return handler.updateTournament(authHeader, id, request);
    }
}