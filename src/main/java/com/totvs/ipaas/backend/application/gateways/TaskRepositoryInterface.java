package com.totvs.ipaas.backend.application.gateways;

import com.totvs.ipaas.backend.domain.models.Task;

import java.util.UUID;

public interface TaskRepositoryInterface {

    Task save(Task task);

    boolean existsById(UUID id);

}
