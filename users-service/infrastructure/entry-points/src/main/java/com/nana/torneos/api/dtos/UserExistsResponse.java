package com.nana.torneos.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserExistsResponse {
    private boolean exists;
}