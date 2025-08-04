package com.totvs.ipaas.backend.application.gateways;

import com.totvs.ipaas.backend.application.pagination.PagedResult;
import com.totvs.ipaas.backend.domain.models.StatusTask;
import com.totvs.ipaas.backend.domain.models.Task;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepositoryInterface {

    Task save(Task task);

    boolean existsById(UUID id);

    Optional<Task> findById(UUID id);

    PagedResult<Task> findByStatusAndUserId(StatusTask status, UUID userId, int page, int size);

}
