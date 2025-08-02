package com.totvs.ipaas.backend.infra.gateways;

import com.totvs.ipaas.backend.application.gateways.TaskRepositoryInterface;
import com.totvs.ipaas.backend.domain.models.Task;
import com.totvs.ipaas.backend.infra.mappers.TaskMapper;
import com.totvs.ipaas.backend.infra.persistence.entities.TaskEntity;
import com.totvs.ipaas.backend.infra.persistence.repositories.TaskRepositoryJpa;

import java.util.Optional;
import java.util.UUID;

public class TaskRepositoryImpl implements TaskRepositoryInterface {

    private final TaskRepositoryJpa repository;
    private final TaskMapper mapper;

    public TaskRepositoryImpl(TaskRepositoryJpa repository, TaskMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Task save(Task task) {
        TaskEntity entity = mapper.toEntity(task);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    @Override
    public Optional<Task> findById(UUID id) {
        Optional<TaskEntity> entity = repository.findById(id);
        return entity.map(mapper::toDomain);
    }

}
