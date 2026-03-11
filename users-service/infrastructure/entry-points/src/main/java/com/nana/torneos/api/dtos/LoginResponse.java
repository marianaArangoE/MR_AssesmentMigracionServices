package com.nana.torneos.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponse {
    private Integer id;
    private String email;
    private String token;
}