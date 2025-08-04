package com.totvs.ipaas.backend.application.usecases.implementations.task;

import com.totvs.ipaas.backend.application.gateways.TaskRepositoryInterface;
import com.totvs.ipaas.backend.application.usecases.interfaces.task.UpdateTaskStatus;
import com.totvs.ipaas.backend.application.validator.TaskValidator;
import com.totvs.ipaas.backend.domain.exception.ResourceNotFoundException;
import com.totvs.ipaas.backend.domain.models.task.StatusTask;
import com.totvs.ipaas.backend.domain.models.task.Task;

import java.time.OffsetDateTime;
import java.util.UUID;

public class UpdateTaskStatusImpl implements UpdateTaskStatus {

    private final TaskRepositoryInterface taskRepository;
    private final TaskValidator taskValidator;

    public UpdateTaskStatusImpl(TaskRepositoryInterface taskRepository, TaskValidator taskValidator) {
        this.taskRepository = taskRepository;
        this.taskValidator = taskValidator;
    }

    @Override
    public void execute(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Task with id %s not found", id)));
        task.setStatus(task.getStatus().getNextStatus());
        if (task.getStatus().equals(StatusTask.COMPLETED)) {
            taskValidator.existOpenSubTasks(id);
            task.setCompletedDate(OffsetDateTime.now());
        }
        taskRepository.save(task);
    }

}
