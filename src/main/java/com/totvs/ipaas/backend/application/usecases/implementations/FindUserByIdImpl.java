package com.totvs.ipaas.backend.application.usecases.implementations;

import com.totvs.ipaas.backend.application.gateways.UserRepositoryInterface;
import com.totvs.ipaas.backend.application.usecases.interfaces.FindUserById;
import com.totvs.ipaas.backend.domain.exception.ResourceNotFoundException;
import com.totvs.ipaas.backend.domain.models.User;
import com.totvs.ipaas.backend.infra.mappers.UserMapper;

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
