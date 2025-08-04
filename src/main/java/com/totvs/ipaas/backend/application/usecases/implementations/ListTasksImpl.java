package com.totvs.ipaas.backend.application.usecases.implementations;

import com.totvs.ipaas.backend.application.command.ListTaskCommand;
import com.totvs.ipaas.backend.application.gateways.TaskRepositoryInterface;
import com.totvs.ipaas.backend.application.pagination.PagedResult;
import com.totvs.ipaas.backend.application.usecases.interfaces.ListTasks;
import com.totvs.ipaas.backend.domain.models.StatusTask;
import com.totvs.ipaas.backend.domain.models.Task;

public class ListTasksImpl implements ListTasks {

    private final TaskRepositoryInterface taskRepository;

    public ListTasksImpl(TaskRepositoryInterface taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public PagedResult<Task> execute(ListTaskCommand command) {
        StatusTask statusTask = StatusTask.valueOf(command.status());
        return taskRepository.findByStatusAndUserId(statusTask, command.userId(), command.page(), command.pageSize());
    }

}
