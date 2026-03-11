package com.nana.torneos.config;

import com.nana.torneos.model.security.PasswordEncryptor;
import com.nana.torneos.model.gateways.UserRepository;
import com.nana.torneos.usecase.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

    @Bean
    public UserUseCase userUseCase(UserRepository userRepository) {
        return new UserUseCase(userRepository);
    }
}