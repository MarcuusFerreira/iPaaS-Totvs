package com.totvs.ipaas.backend.application.usecases.interfaces;

import com.totvs.ipaas.backend.application.command.CreateSubTaskCommand;
import com.totvs.ipaas.backend.domain.models.SubTask;

public interface SaveSubTask {

    SubTask execute(CreateSubTaskCommand command);

}
