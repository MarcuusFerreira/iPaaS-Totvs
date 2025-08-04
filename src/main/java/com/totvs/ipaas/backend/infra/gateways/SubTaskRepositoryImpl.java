package com.totvs.ipaas.backend.infra.gateways;

import com.totvs.ipaas.backend.application.gateways.SubTaskRepositoryInterface;
import com.totvs.ipaas.backend.application.pagination.PagedResult;
import com.totvs.ipaas.backend.domain.exception.ResourceNotFoundException;
import com.totvs.ipaas.backend.domain.models.subtask.StatusSubTask;
import com.totvs.ipaas.backend.domain.models.subtask.SubTask;
import com.totvs.ipaas.backend.infra.mappers.SubTaskMapper;
import com.totvs.ipaas.backend.infra.persistence.entities.subtask.SubTaskEntity;
import com.totvs.ipaas.backend.infra.persistence.entities.subtask.StatusSubTaskEntity;
import com.totvs.ipaas.backend.infra.persistence.repositories.SubTaskRepositoryJpa;
import com.totvs.ipaas.backend.infra.persistence.specifications.SubTaskSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public boolean existsOpenSubTasks(UUID taskId) {
        return subTaskRepository.existsByStatusNotAndTaskEntityId(StatusSubTaskEntity.COMPLETED, taskId);
    }

    @Override
    public PagedResult<SubTask> findByTaskIdAndStatus(UUID taskId, StatusSubTask status, int page, int size) {
        Specification<SubTaskEntity> spec = Specification.
                where(SubTaskSpecification.hasTaskId(taskId));
        if (status != null) {
            spec = spec.and(SubTaskSpecification.hasStatus(StatusSubTaskEntity.valueOf(status.getValue())));
        }
        Page<SubTaskEntity> resultEntity = subTaskRepository.findAll(spec, PageRequest.of(page, size));
        if (!resultEntity.hasContent()) {
            StringBuilder stringBuilder = new StringBuilder()
                    .append("SubTasks not found with task id ").append(taskId);
            if (status != null) {
                stringBuilder.append(" and status ").append(status);
            }
            throw new ResourceNotFoundException(
                    stringBuilder.toString()
            );
        }
        List<SubTask> resultDomain = resultEntity.getContent().stream().map(subTaskMapper::toDomain).collect(Collectors.toList());
        return new PagedResult<SubTask>(
                resultEntity.getNumber(),
                resultEntity.getSize(),
                resultEntity.getTotalElements(),
                resultEntity.getTotalPages(),
                resultDomain
        );
    }

}
