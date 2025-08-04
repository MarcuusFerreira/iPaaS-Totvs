package com.totvs.ipaas.backend.application.gateways;

import com.totvs.ipaas.backend.application.pagination.PagedResult;
import com.totvs.ipaas.backend.domain.models.subtask.StatusSubTask;
import com.totvs.ipaas.backend.domain.models.subtask.SubTask;

import java.util.Optional;
import java.util.UUID;

public interface SubTaskRepositoryInterface {

    SubTask save(SubTask subTask);

    Optional<SubTask> findById(UUID id);

    boolean existsById(UUID id);

    boolean existsOpenSubTasks(UUID id);

    PagedResult<SubTask> findByTaskIdAndStatus(UUID taskId, StatusSubTask status,  int page, int size);

}
