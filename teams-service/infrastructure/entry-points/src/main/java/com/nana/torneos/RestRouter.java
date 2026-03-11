package com.nana.torneos;

import com.nana.torneos.dtos.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class RestRouter {

    private final TeamsHandler handler;

    // A. Listar equipos
    @GetMapping
    public Flux<TeamResponse> getAll(
//            @RequestHeader(value = "Authorization", required = false) String authHeader
    )
    {
        return handler.getTeams();
    }


    @GetMapping("/{teamId}")
    public Mono<TeamResponse> getById(@PathVariable("teamId") Integer teamId) {
        return handler.getTeamById(teamId);
    }

    @PostMapping
    public Mono<TeamResponse> create(@Valid @RequestBody CreateTeamRequest request) {
        return handler.createTeam(request);
    }

    @PutMapping("/{teamId}")
    public Mono<TeamResponse> update(
            @PathVariable("teamId") Integer teamId,
            @RequestHeader("X-User-Id") Integer requesterId,
            @RequestBody UpdateTeamRequest request
    ) {
        return handler.updateTeam(teamId, requesterId, request);
    }


    @PostMapping("/{teamId}/members")
    public Mono<MessageResponse> registerUserToTeam(
            @PathVariable("teamId") Integer teamId,
            @Valid @RequestBody AddMemberRequest request
    ) {
        return handler.registerUserToTeam(teamId, request);
    }

    @DeleteMapping("/{teamId}/members/{userId}")
    public Mono<MessageResponse> removeMemberFromTeam(
            @PathVariable("teamId") Integer teamId,
            @PathVariable("userId") Integer userId,
            @RequestHeader("X-User-Id") Integer requesterId
    ) {
        return handler.removeMemberFromTeam(teamId, userId, requesterId);
    }

    @PostMapping("/{teamId}/leave")
    public Mono<MessageResponse> leaveTeam(
            @PathVariable("teamId") Integer teamId,
            @Valid @RequestBody AddMemberRequest request
    ) {
        return handler.leaveTeam(teamId, request);
    }
}