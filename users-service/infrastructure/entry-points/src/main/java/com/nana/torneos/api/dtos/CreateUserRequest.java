package com.nana.torneos.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @JsonProperty("id_usuario")
    @NotNull(message = "El id del usuario es requerido")
    private Integer id;

    @JsonProperty("nombre")
    @NotBlank(message = "El nombre del usuario es requerido")
    private String nombre;

    @JsonProperty("correo")
    @NotBlank(message = "El correo del usuario es requerido")
    @Email(message = "Formato de correo inválido")
    private String correo;

    @JsonProperty("contrasena")
    @NotBlank(message = "La contraseña del usuario es requerida")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String contrasena;

    @JsonProperty("apodo")
    @NotBlank(message = "El apodo del usuario es requerido")
    private String apodo;
}