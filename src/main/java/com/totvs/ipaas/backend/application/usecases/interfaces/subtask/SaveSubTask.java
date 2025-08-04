package com.totvs.ipaas.backend.application.usecases.interfaces.subtask;

import com.totvs.ipaas.backend.application.command.subtask.CreateSubTaskCommand;
import com.totvs.ipaas.backend.domain.models.subtask.SubTask;

public interface SaveSubTask {

    SubTask execute(CreateSubTaskCommand command);

}
