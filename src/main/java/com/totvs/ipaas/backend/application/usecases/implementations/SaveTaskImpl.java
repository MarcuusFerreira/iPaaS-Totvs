package com.totvs.ipaas.backend.application.usecases.implementations;

import com.totvs.ipaas.backend.application.command.CreateTaskCommand;
import com.totvs.ipaas.backend.application.gateways.TaskRepositoryInterface;
import com.totvs.ipaas.backend.application.usecases.interfaces.SaveTask;
import com.totvs.ipaas.backend.application.validator.UserValidator;
import com.totvs.ipaas.backend.domain.models.Task;

public class SaveTaskImpl implements SaveTask {

    private final TaskRepositoryInterface taskRepository;
    private final UserValidator userValidator;

    public SaveTaskImpl(TaskRepositoryInterface taskRepository, UserValidator userValidator) {
        this.taskRepository = taskRepository;
        this.userValidator = userValidator;
    }

    @Override
    public Task execute(CreateTaskCommand createTaskCommand) {
        userValidator.existsByUserId(createTaskCommand.userId());
        String title = createTaskCommand.title().toLowerCase().trim();
        String description = createTaskCommand.description() != null ? createTaskCommand.description().toLowerCase().trim() : null;
        Task task = new Task(title, description, createTaskCommand.userId());
        return taskRepository.save(task);
    }
}
