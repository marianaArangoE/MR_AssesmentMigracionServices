package com.nana.torneos.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginRequest {
    @JsonProperty("correo")
    private String correo;

    @JsonProperty("contrasena")
    private String contrasena;
}
