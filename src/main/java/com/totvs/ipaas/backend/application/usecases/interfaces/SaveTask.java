package com.totvs.ipaas.backend.application.usecases.interfaces;

import com.totvs.ipaas.backend.application.command.CreateTaskCommand;
import com.totvs.ipaas.backend.domain.models.Task;

public interface SaveTask {

    Task execute(CreateTaskCommand createTaskCommand);

}
