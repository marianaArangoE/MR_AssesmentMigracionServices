package com.nana.torneos.usecase;

import com.nana.torneos.model.exceptions.UserErrors;
import com.nana.torneos.model.security.PasswordEncryptor;
import com.nana.torneos.model.gateways.UserRepository;
import com.nana.torneos.model.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncryptor passwordEncryptor;

    public Mono<User> execute(User userRequest) {

        // ✅ 1) Validar ID obligatorio
        if (userRequest.getId() == null) {
            return Mono.error(UserErrors.idIsRequired()); // crea este error si no existe
        }

        String normalizedEmail = userRequest.getEmail().trim().toLowerCase();
        String normalizedNickname = userRequest.getNickname().trim().toLowerCase();

        return userRepository.existsById(userRequest.getId())
                .flatMap(idExists -> {
                    if (idExists) {
                        return Mono.error(UserErrors.idAlreadyExists());
                    }
                    return userRepository.existsByEmail(normalizedEmail);
                })
                .flatMap(emailExists -> {
                    if (emailExists) {
                        return Mono.error(UserErrors.emailAlreadyExists());
                    }
                    return userRepository.existsByNickname(normalizedNickname);
                })
                .flatMap(nicknameExists -> {
                    if (nicknameExists) {
                        return Mono.error(UserErrors.nicknameAlreadyExists());
                    }

                    String encryptedPassword =
                            passwordEncryptor.encode(userRequest.getPassword());

                    User userToSave = User.builder()
                            .id(userRequest.getId()) // ✅ ID identificación
                            .name(userRequest.getName().trim())
                            .email(normalizedEmail)
                            .password(encryptedPassword)
                            .nickname(normalizedNickname)
                            .build();

                    return userRepository.save(userToSave);
                });
    }
}