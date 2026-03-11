package com.nana.torneos.api;

import com.nana.torneos.api.dtos.*;
import com.nana.torneos.api.security.JwtService;
import com.nana.torneos.api.mapper.UserEntryMapper;
import com.nana.torneos.model.exceptions.UserErrors;
import com.nana.torneos.model.users.User;
import com.nana.torneos.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
@RequiredArgsConstructor
public class UserHandler {

    private final CreateUserUseCase createUserUseCase;
    private final LoginUseCase loginUseCase;
    private final JwtService jwtService;
    private final UserUseCase userUseCase;
    private final UserExistsUseCase userExistsUseCase;
    private final GetUserTeamUseCase getUserTeamUseCase;
    private final AssignUserToTeamUseCase assignUserToTeamUseCase;
    private final RemoveUserFromTeamUseCase removeUserFromTeamUseCase;
//    private final GetUserByIdUseCase getUserByIdUseCase;

    public Mono<UserResponse> create(CreateUserRequest request) {
        User user = UserEntryMapper.toDomain(request);

        return createUserUseCase.execute(user)
                .doOnError(e -> e.printStackTrace())  // <--- temporal
                .map(UserEntryMapper::toResponse);
    }


    public Mono<LoginResponse> login(LoginRequest request) {

        return loginUseCase.execute(
                request.getCorreo(),
                request.getContrasena()
        ).map(user -> {

            String token = jwtService.generateToken(
                    user.getId(),
                    user.getEmail().trim()
            );

            return new LoginResponse(
                    user.getId(),
                    user.getEmail().trim(),
                    token
            );
        });
    }
    public Mono<UserResponse> getMe(String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return Mono.error(UserErrors.invalidToken());
        }

        String token = authorizationHeader.substring(7);

        Integer userId;

        try {
            userId = jwtService.extractUserId(token);
        } catch (Exception e) {
            return Mono.error(UserErrors.invalidToken());
        }

        return userUseCase.findById(userId)
                .switchIfEmpty(Mono.error(UserErrors.userNotFound()))
                .map(UserEntryMapper::toResponse);
    }

    public Mono<UserExistsResponse> exists(Integer userId) {
        return userExistsUseCase.execute(userId)
                .map(UserExistsResponse::new);
    }

    public Mono<UserTeamResponse> getUserTeam(Integer userId) {
        return getUserTeamUseCase.execute(userId)
                .map(user -> UserTeamResponse.builder()
                        .userId(user.getId())
                        .teamId(user.getTeamId())
                        .build());
    }

    public Mono<UserTeamResponse> assignUserToTeam(Integer userId, UpdateUserTeamRequest request) {
        return assignUserToTeamUseCase.execute(userId, request.getTeamId())
                .map(user -> UserTeamResponse.builder()
                        .userId(user.getId())
                        .teamId(user.getTeamId())
                        .build());
    }

    public Mono<UserTeamResponse> removeUserFromTeam(Integer userId) {
        return removeUserFromTeamUseCase.execute(userId)
                .map(user -> UserTeamResponse.builder()
                        .userId(user.getId())
                        .teamId(user.getTeamId())
                        .build());
    }

}