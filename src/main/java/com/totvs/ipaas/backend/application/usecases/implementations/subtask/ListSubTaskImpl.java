package com.totvs.ipaas.backend.application.usecases.implementations.subtask;

import com.totvs.ipaas.backend.application.command.subtask.ListSubTaskCommand;
import com.totvs.ipaas.backend.application.gateways.SubTaskRepositoryInterface;
import com.totvs.ipaas.backend.application.pagination.PagedResult;
import com.totvs.ipaas.backend.application.usecases.interfaces.subtask.ListSubTask;
import com.totvs.ipaas.backend.domain.models.subtask.StatusSubTask;
import com.totvs.ipaas.backend.domain.models.subtask.SubTask;

public class ListSubTaskImpl implements ListSubTask {

    private final SubTaskRepositoryInterface subTaskRepository;

    public ListSubTaskImpl(SubTaskRepositoryInterface subTaskRepository) {
        this.subTaskRepository = subTaskRepository;
    }

    @Override
    public PagedResult<SubTask> execute(ListSubTaskCommand command) {
        StatusSubTask status = null;
        if (command.status() != null) {
            status = StatusSubTask.valueOf(command.status());
        }
        System.out.println(status);
        return subTaskRepository.findByTaskIdAndStatus(
                command.taskId(),
                status,
                command.page(),
                command.size()
        );
    }

}
