package com.totvs.ipaas.backend.application.usecases.implementations.task;

import com.totvs.ipaas.backend.application.command.task.ListTaskCommand;
import com.totvs.ipaas.backend.application.gateways.TaskRepositoryInterface;
import com.totvs.ipaas.backend.application.pagination.PagedResult;
import com.totvs.ipaas.backend.application.usecases.interfaces.task.ListTasks;
import com.totvs.ipaas.backend.domain.models.task.StatusTask;
import com.totvs.ipaas.backend.domain.models.task.Task;

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
