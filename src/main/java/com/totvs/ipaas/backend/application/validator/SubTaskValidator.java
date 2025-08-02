package com.totvs.ipaas.backend.application.validator;

import com.totvs.ipaas.backend.application.gateways.SubTaskRepositoryInterface;
import com.totvs.ipaas.backend.domain.exception.ValidationException;

import java.util.UUID;

public class SubTaskValidator {

    private final SubTaskRepositoryInterface subTaskRepository;

    public SubTaskValidator(SubTaskRepositoryInterface subTaskRepository) {
        this.subTaskRepository = subTaskRepository;
    }

    public void existsById(UUID id) {
        boolean exist = subTaskRepository.existsById(id);
        if (!exist) {
            throw new ValidationException(String.format("User with id %s not exist. Please check the input data", id));
        }
    }

}
