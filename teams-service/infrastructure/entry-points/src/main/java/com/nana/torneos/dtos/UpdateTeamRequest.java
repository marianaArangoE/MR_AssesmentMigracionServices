package com.nana.torneos.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTeamRequest {

    @JsonProperty("nombre_new")
    private String nameNew;

    @JsonProperty("siglas_new")
    private String acronymNew;
}
