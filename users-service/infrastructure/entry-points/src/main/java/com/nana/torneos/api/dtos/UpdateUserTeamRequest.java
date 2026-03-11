package com.nana.torneos.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserTeamRequest {

    @JsonProperty("team_id")
    @NotNull(message = "El team id es requerido")
    private Integer teamId;
}