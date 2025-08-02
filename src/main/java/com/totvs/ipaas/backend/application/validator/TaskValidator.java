package com.totvs.ipaas.backend.application.validator;

import com.totvs.ipaas.backend.application.gateways.SubTaskRepositoryInterface;
import com.totvs.ipaas.backend.application.gateways.TaskRepositoryInterface;
import com.totvs.ipaas.backend.domain.exception.ValidationException;

import java.util.UUID;

public class TaskValidator {

    private final TaskRepositoryInterface taskRepository;
    private final SubTaskRepositoryInterface subTaskRepository;

    public TaskValidator(TaskRepositoryInterface taskRepository, SubTaskRepositoryInterface  subTaskRepository) {
        this.taskRepository = taskRepository;
        this.subTaskRepository = subTaskRepository;
    }

    public void existsByTaskId(UUID taskId) {
        boolean exist = taskRepository.existsById(taskId);
        if (!exist) {
            throw new ValidationException(
                    String.format("Task with id %s does not exist. Please check the input data",  taskId)
            );
        }
    }

    public void existOpenSubTasks(UUID taskId) {
        boolean exist = subTaskRepository.existsOpenSubTasks(taskId);
        if (exist) {
            throw new ValidationException(
                    String.format("There are open subtasks for the task with id %s", taskId)
            );
        }
    }
}
