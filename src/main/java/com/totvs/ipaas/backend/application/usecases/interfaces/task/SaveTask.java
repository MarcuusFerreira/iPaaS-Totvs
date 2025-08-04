package com.totvs.ipaas.backend.application.usecases.interfaces.task;

import com.totvs.ipaas.backend.application.command.task.CreateTaskCommand;
import com.totvs.ipaas.backend.domain.models.task.Task;

public interface SaveTask {

    Task execute(CreateTaskCommand createTaskCommand);

}
