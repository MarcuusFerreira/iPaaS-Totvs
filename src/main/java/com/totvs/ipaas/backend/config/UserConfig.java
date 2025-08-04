package com.totvs.ipaas.backend.config;

import com.totvs.ipaas.backend.application.gateways.UserRepositoryInterface;
import com.totvs.ipaas.backend.application.usecases.implementations.user.FindUserByIdImpl;
import com.totvs.ipaas.backend.application.usecases.implementations.user.SaveUserImpl;
import com.totvs.ipaas.backend.application.usecases.interfaces.user.FindUserById;
import com.totvs.ipaas.backend.application.usecases.interfaces.user.SaveUser;
import com.totvs.ipaas.backend.application.validator.UserValidator;
import com.totvs.ipaas.backend.infra.gateways.UserRepositoryImpl;
import com.totvs.ipaas.backend.infra.mappers.UserMapper;
import com.totvs.ipaas.backend.infra.persistence.repositories.UserRepositoryJpa;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }

    @Bean
    public UserRepositoryInterface userRepositoryInterface(UserRepositoryJpa userRepositoryJpa, UserMapper userMapper) {
        return new UserRepositoryImpl(userRepositoryJpa, userMapper);
    }

    @Bean
    public UserValidator userValidator(UserRepositoryInterface userRepositoryInterface) {
        return new UserValidator(userRepositoryInterface);
    }

    @Bean
    public SaveUser saveUser(UserRepositoryInterface userRepository, UserValidator userValidator) {
        return new SaveUserImpl(userRepository, userValidator);
    }

    @Bean
    public FindUserById findUserById(UserRepositoryInterface userRepository) {
        return new FindUserByIdImpl(userRepository);
    }

}
