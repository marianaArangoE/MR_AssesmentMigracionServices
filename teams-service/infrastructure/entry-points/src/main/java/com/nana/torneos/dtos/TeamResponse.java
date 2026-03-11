package com.nana.torneos.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponse {

    @JsonProperty("id_equipo")
    private Integer id;

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("siglas")
    private String acronym;

    @JsonProperty("id_creador")
    private Integer creatorId;
}
