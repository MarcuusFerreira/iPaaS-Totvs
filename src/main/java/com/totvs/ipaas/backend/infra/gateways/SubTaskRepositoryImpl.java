package com.totvs.ipaas.backend.infra.gateways;

import com.totvs.ipaas.backend.application.gateways.SubTaskRepositoryInterface;
import com.totvs.ipaas.backend.domain.models.SubTask;
import com.totvs.ipaas.backend.infra.mappers.SubTaskMapper;
import com.totvs.ipaas.backend.infra.persistence.entities.SubTaskEntity;
import com.totvs.ipaas.backend.infra.persistence.repositories.SubTaskRepositoryJpa;

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
}
