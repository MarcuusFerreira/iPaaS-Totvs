package com.totvs.ipaas.backend.application.usecases.interfaces;

import com.totvs.ipaas.backend.application.command.ListSubTaskCommand;
import com.totvs.ipaas.backend.application.pagination.PagedResult;
import com.totvs.ipaas.backend.domain.models.SubTask;

public interface ListSubTask {

    PagedResult<SubTask> execute(ListSubTaskCommand command);

}
