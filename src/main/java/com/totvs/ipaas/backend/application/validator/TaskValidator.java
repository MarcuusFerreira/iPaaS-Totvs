package com.totvs.ipaas.backend.application.validator;

import com.totvs.ipaas.backend.application.gateways.TaskRepositoryInterface;
import com.totvs.ipaas.backend.domain.exception.ValidationException;

import java.util.UUID;

public class TaskValidator {

    private final TaskRepositoryInterface taskRepository;

    public TaskValidator(TaskRepositoryInterface taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void existsByTaskId(UUID taskId) {
        boolean exist = taskRepository.existsById(taskId);
        if (!exist) {
            throw new ValidationException(
                    String.format("Task with id %s does not exist. Please check the input data",  taskId)
            );
        }
    }
}
