package com.totvs.ipaas.backend.application.validator;

import com.totvs.ipaas.backend.application.gateways.UserRepositoryInterface;
import com.totvs.ipaas.backend.domain.exception.EmailAlreadyExistsException;
import com.totvs.ipaas.backend.domain.exception.ValidationException;

import java.util.UUID;

public class UserValidator {

    private final UserRepositoryInterface userRepository;

    public UserValidator(UserRepositoryInterface userRepository) {
        this.userRepository = userRepository;
    }

    public void existsByEmail(String email) {
        boolean exist = userRepository.existsByEmail(email);
        if (exist) {
            throw new EmailAlreadyExistsException("Email not available");
        }
    }

    public void existsByUserId(UUID userId) {
        boolean exist = userRepository.existsById(userId);
        if (!exist) {
            throw new ValidationException(String.format("User with id %s not exist. Please check the input data", userId));
        }
    }

}
