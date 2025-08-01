package com.totvs.ipaas.backend.application.validator;

import com.totvs.ipaas.backend.application.gateways.UserRepositoryInterface;
import com.totvs.ipaas.backend.domain.exception.EmailAlreadyExistsException;

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

}
