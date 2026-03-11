package com.nana.torneos.mapper;
import com.nana.torneos.dtos.CreateTeamRequest;
import com.nana.torneos.dtos.TeamResponse;
import com.nana.torneos.Teams;

public class TeamMapper {

    private TeamMapper() {}

    public static Teams toDomain(CreateTeamRequest req) {
        return Teams.builder()
                .name(req.getName())
                .acronym(req.getAcronym())
                .creatorId(req.getCreatorId())
                .build();
    }

    public static TeamResponse toResponse(Teams team) {
        return TeamResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .acronym(team.getAcronym())
                .creatorId(team.getCreatorId())
                .build();
    }
}