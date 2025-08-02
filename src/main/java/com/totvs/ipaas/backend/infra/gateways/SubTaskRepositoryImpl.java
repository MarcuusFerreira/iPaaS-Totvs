package com.totvs.ipaas.backend.infra.gateways;

import com.totvs.ipaas.backend.application.gateways.SubTaskRepositoryInterface;
import com.totvs.ipaas.backend.domain.models.SubTask;
import com.totvs.ipaas.backend.infra.mappers.SubTaskMapper;
import com.totvs.ipaas.backend.infra.persistence.entities.SubTaskEntity;
import com.totvs.ipaas.backend.infra.persistence.repositories.SubTaskRepositoryJpa;

import java.util.Optional;
import java.util.UUID;

public class SubTaskRepositoryImpl implements SubTaskRepositoryInterface {

    private final SubTaskRepositoryJpa subTaskRepository;
    private final SubTaskMapper subTaskMapper;

    public SubTaskRepositoryImpl(SubTaskRepositoryJpa subTaskRepository, SubTaskMapper subTaskMapper) {
        this.subTaskRepository = subTaskRepository;
        this.subTaskMapper = subTaskMapper;
    }

    @Override
    public SubTask save(SubTask subTask) {
        SubTaskEntity entity = subTaskMapper.toEntity(subTask);
        return subTaskMapper.toDomain(subTaskRepository.save(entity));
    }

    @Override
    public Optional<SubTask> findById(UUID id) {
        Optional<SubTaskEntity> entity = subTaskRepository.findById(id);
        return entity.map(subTaskMapper::toDomain);
    }

    @Override
    public boolean existsById(UUID id) {
        return subTaskRepository.existsById(id);
    }
}
