package com.totvs.ipaas.backend.application.usecases.interfaces.subtask;

import com.totvs.ipaas.backend.application.command.subtask.ListSubTaskCommand;
import com.totvs.ipaas.backend.application.pagination.PagedResult;
import com.totvs.ipaas.backend.domain.models.subtask.SubTask;

public interface ListSubTask {

    PagedResult<SubTask> execute(ListSubTaskCommand command);

}
