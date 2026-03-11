package com.nana.torneos.config.clientResponses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserTeamResponse {

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("team_id")
    private Integer teamId;
}