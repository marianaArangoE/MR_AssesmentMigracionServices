package com.nana.torneos.api.mapper;

import com.nana.torneos.api.dtos.CreateUserRequest;
import com.nana.torneos.api.dtos.UserResponse;
import com.nana.torneos.model.users.User;

public class UserEntryMapper {
    public static User toDomain(CreateUserRequest dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getNombre())
                .nickname(dto.getApodo())
                .email(dto.getCorreo())
                .password(dto.getContrasena())
                .build();
    }

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .nombre(user.getName() != null ? user.getName().trim() : null)
                .apodo(user.getNickname() != null ? user.getNickname().trim() : null)
                .correo(user.getEmail() != null ? user.getEmail().trim() : null)
                .build();
    }
}
