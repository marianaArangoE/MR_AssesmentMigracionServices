package com.nana.torneos.adapters.db;

import com.nana.torneos.model.gateways.UserRepository;
import com.nana.torneos.model.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class R2dbcAdapter implements UserRepository {

    private final R2DBCUserRepository repository;

    @Override
    public Mono<Boolean> existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Mono<Boolean> existsByNickname(String nickname) {
        return repository.existsByNickname(nickname);
    }

    @Override
    public Mono<User> save(User user) {
        UserData data = toData(user);

        // Si no hay ID, es nuevo (INSERT)
        if (user.getId() == null) {
            return repository.save(data.markNew())
                    .map(this::toEntity);
        }

        // Si hay ID, decidimos si UPDATE o INSERT según exista en DB
        return repository.existsById(user.getId())
                .flatMap(exists -> repository.save(exists ? data.markNotNew() : data.markNew()))
                .map(this::toEntity);
    }

    @Override
    public Mono<User> findById(Integer id) {
        return repository.findById(id)
                .map(this::toEntity);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(this::toEntity);
    }

    private UserData toData(User user) {
        return UserData.builder()
                .id(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .password(user.getPassword())
                .teamId(user.getTeamId())
                .build();
    }

    private User toEntity(UserData data) {
        return User.builder()
                .id(data.getId())
                .name(data.getName())
                .nickname(data.getNickname())
                .email(data.getEmail())
                .password(data.getPassword())
                .teamId(data.getTeamId())
                .build();
    }
}