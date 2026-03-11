package com.nana.torneos;

import com.nana.torneos.dtos.*;
import com.nana.torneos.mapper.TeamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TeamsHandler {


    private final GetTeamsUseCase getTeamsUseCase;
    private final GetTeamByIdUseCase getTeamByIdUseCase;
    private final CreateTeamUseCase createTeamUseCase;
    private final UpdateTeamUseCase updateTeamUseCase;
    private final RegisterUserToTeamUseCase registerUserToTeamUseCase;
    private final RemoveMemberFromTeamUseCase removeMemberFromTeamUseCase;
    private final LeaveTeamUseCase leaveTeamUseCase;

    public Flux<TeamResponse> getTeams() {
        return getTeamsUseCase.execute()
                .map(TeamMapper::toResponse);
    }


    public Mono<TeamResponse> getTeamById(Integer teamId) {
        return getTeamByIdUseCase.execute(teamId)
                .map(TeamMapper::toResponse);
    }

    public Mono<TeamResponse> createTeam(CreateTeamRequest request) {
        return createTeamUseCase.execute(TeamMapper.toDomain(request))
                .map(TeamMapper::toResponse);
    }

    public Mono<TeamResponse> updateTeam(Integer teamId, Integer requesterId, UpdateTeamRequest request) {
        return updateTeamUseCase.execute(teamId, requesterId, request.getNameNew(), request.getAcronymNew())
                .map(TeamMapper::toResponse);
    }

    public Mono<MessageResponse> registerUserToTeam(Integer teamId, AddMemberRequest request) {
        return registerUserToTeamUseCase.execute(teamId, request.getUserId())
                .map(team -> new MessageResponse("Usuario registrado en el equipo correctamente"));
    }



    public Mono<MessageResponse> removeMemberFromTeam(Integer teamId, Integer userId, Integer requesterId) {
        return removeMemberFromTeamUseCase.execute(teamId, userId, requesterId)
                .map(MessageResponse::new);
    }

    public Mono<MessageResponse> leaveTeam(Integer teamId, AddMemberRequest request) {
        return leaveTeamUseCase.execute(teamId, request.getUserId())
                .map(MessageResponse::new);
    }
}
