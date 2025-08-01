package com.totvs.ipaas.backend.application.usecases.implementations;

import com.totvs.ipaas.backend.application.command.CreateUserCommand;
import com.totvs.ipaas.backend.application.gateways.UserRepositoryInterface;
import com.totvs.ipaas.backend.application.usecases.interfaces.SaveUser;
import com.totvs.ipaas.backend.application.validator.UserValidator;
import com.totvs.ipaas.backend.domain.models.User;

public class SaveUserImpl implements SaveUser {

    private final UserRepositoryInterface userRepository;
    private final UserValidator validator;

    public SaveUserImpl(UserRepositoryInterface userRepository, UserValidator validator) {
        this.userRepository = userRepository;
        this.validator = validator;
    }

    @Override
    public User execute(CreateUserCommand command) {
        String name = command.name().toLowerCase().trim();
        String email = command.email().toLowerCase().trim();
        validator.existsByEmail(email);
        User user = new User(name, email);
        return userRepository.save(user);
    }

}
