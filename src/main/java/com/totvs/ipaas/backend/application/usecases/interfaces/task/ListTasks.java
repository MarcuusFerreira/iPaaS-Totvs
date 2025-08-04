package com.totvs.ipaas.backend.application.usecases.interfaces.task;

import com.totvs.ipaas.backend.application.command.task.ListTaskCommand;
import com.totvs.ipaas.backend.application.pagination.PagedResult;
import com.totvs.ipaas.backend.domain.models.task.Task;

public interface ListTasks {

    PagedResult<Task> execute(ListTaskCommand command);

}
