package com.nana.torneos.api;

import com.nana.torneos.api.dtos.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class RestRouter {

    private final UserHandler userHandler;


    @PostMapping
    public Mono<UserResponse> createUser(@Valid @RequestBody CreateUserRequest req) {
        return userHandler.create(req);
    }
    @PostMapping("/login")
    public Mono<LoginResponse> login(@RequestBody LoginRequest request) {
        return userHandler.login(request);
    }
    @GetMapping("/me")
    public Mono<UserResponse> getMe(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        return userHandler.getMe(authorizationHeader);
    }
    @GetMapping("/{id}/exists")
    public Mono<UserExistsResponse> exists(@PathVariable("id") Integer id) {
        return userHandler.exists(id);
    }

    @GetMapping("/{id}/team")
    public Mono<UserTeamResponse> getUserTeam(@PathVariable("id") Integer id) {
        return userHandler.getUserTeam(id);
    }

    @PutMapping("/{id}/team")
    public Mono<UserTeamResponse> assignUserToTeam(
            @PathVariable("id") Integer id,
            @Valid @RequestBody UpdateUserTeamRequest request
    ) {
        return userHandler.assignUserToTeam(id, request);
    }

    @DeleteMapping("/{id}/team")
    public Mono<UserTeamResponse> removeUserFromTeam(@PathVariable("id") Integer id) {
        return userHandler.removeUserFromTeam(id);
    }


}