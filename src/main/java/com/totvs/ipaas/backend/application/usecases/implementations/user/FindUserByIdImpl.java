package com.totvs.ipaas.backend.application.usecases.implementations.user;

import com.totvs.ipaas.backend.application.gateways.UserRepositoryInterface;
import com.totvs.ipaas.backend.application.usecases.interfaces.user.FindUserById;
import com.totvs.ipaas.backend.domain.exception.ResourceNotFoundException;
import com.totvs.ipaas.backend.domain.models.user.User;

import java.util.UUID;

public class FindUserByIdImpl implements FindUserById {

    private final UserRepositoryInterface userRepository;

    public FindUserByIdImpl(UserRepositoryInterface userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format("User with id %s not found", id)
        ));
    }
}
