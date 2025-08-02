package com.totvs.ipaas.backend.application.usecases.implementations;

import com.totvs.ipaas.backend.application.command.CreateSubTaskCommand;
import com.totvs.ipaas.backend.application.gateways.SubTaskRepositoryInterface;
import com.totvs.ipaas.backend.application.usecases.interfaces.SaveSubTask;
import com.totvs.ipaas.backend.application.validator.TaskValidator;
import com.totvs.ipaas.backend.domain.models.SubTask;

public class SaveSubTaskImpl implements SaveSubTask {

    private final SubTaskRepositoryInterface subTaskRepository;
    private final TaskValidator taskValidator;

    public  SaveSubTaskImpl(SubTaskRepositoryInterface subTaskRepository, TaskValidator  taskValidator) {
        this.subTaskRepository = subTaskRepository;
        this.taskValidator = taskValidator;
    }

    @Override
    public SubTask execute(CreateSubTaskCommand command) {
        taskValidator.existsByTaskId(command.taskId());
        String title = command.title().toLowerCase().trim();
        String description = command.description() != null ?  command.description().toLowerCase().trim() : null;
        SubTask subTask = new SubTask(title, description, command.taskId());
        return subTaskRepository.save(subTask);
    }

}
