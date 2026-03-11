package com.nana.torneos.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTeamResponse {

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("team_id")
    private Integer teamId;
}
