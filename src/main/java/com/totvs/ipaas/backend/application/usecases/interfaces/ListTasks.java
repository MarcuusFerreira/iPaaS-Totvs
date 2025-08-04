package com.totvs.ipaas.backend.application.usecases.interfaces;

import com.totvs.ipaas.backend.application.command.ListTaskCommand;
import com.totvs.ipaas.backend.application.pagination.PagedResult;
import com.totvs.ipaas.backend.domain.models.Task;

public interface ListTasks {

    PagedResult<Task> execute(ListTaskCommand command);

}
