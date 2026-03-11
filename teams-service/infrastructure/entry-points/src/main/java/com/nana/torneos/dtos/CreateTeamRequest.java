package com.nana.torneos.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeamRequest {

    @JsonProperty("nombre")
    @NotBlank(message = "El nombre del equipo es requerido")
    private String name;

    @JsonProperty("siglas")
    @NotBlank(message = "Las siglas del equipo son requeridas")
    @Size(max = 6, message = "Las siglas deben tener maximo 6 caracteres")
    private String acronym;

    @JsonProperty("id_creador")
    @NotNull(message = "El id del creador es requerido")
    private Integer creatorId;
}