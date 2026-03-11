package com.nana.torneos.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserResponse {
    private Integer id;
    private String nombre;
    private String correo;
    private String apodo;
}