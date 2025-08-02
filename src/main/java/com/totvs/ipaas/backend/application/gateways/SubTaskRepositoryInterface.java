package com.totvs.ipaas.backend.application.gateways;

import com.totvs.ipaas.backend.domain.models.SubTask;

import java.util.Optional;
import java.util.UUID;

public interface SubTaskRepositoryInterface {

    SubTask save(SubTask subTask);

    Optional<SubTask> findById(UUID id);

    boolean existsById(UUID id);

    boolean existsOpenSubTasks(UUID id);

}
