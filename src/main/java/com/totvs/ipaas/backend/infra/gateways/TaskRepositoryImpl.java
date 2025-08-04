package com.totvs.ipaas.backend.infra.gateways;

import com.totvs.ipaas.backend.application.gateways.TaskRepositoryInterface;
import com.totvs.ipaas.backend.application.pagination.PagedResult;
import com.totvs.ipaas.backend.domain.exception.ResourceNotFoundException;
import com.totvs.ipaas.backend.domain.models.task.StatusTask;
import com.totvs.ipaas.backend.domain.models.task.Task;
import com.totvs.ipaas.backend.infra.mappers.TaskMapper;
import com.totvs.ipaas.backend.infra.persistence.entities.task.TaskEntity;
import com.totvs.ipaas.backend.infra.persistence.entities.task.StatusTaskEntity;
import com.totvs.ipaas.backend.infra.persistence.repositories.TaskRepositoryJpa;
import com.totvs.ipaas.backend.infra.persistence.specifications.TaskSpecification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public PagedResult<Task> findByStatusAndUserId(StatusTask status, UUID userId, int page, int size) {
        List<Specification<TaskEntity>> specifications = new ArrayList<>();
        specifications.add(TaskSpecification.hasStatus(StatusTaskEntity.valueOf(status.getValue())));
        if (userId != null) {
            specifications.add(TaskSpecification.hasUserId(userId));
        }
        Specification<TaskEntity> spec = specifications.stream()
                .filter(Objects::nonNull)
                .reduce(Specification.unrestricted(), Specification::and);
        Page<TaskEntity> resultEntity = repository.findAll(spec, PageRequest.of(page, size));
        if (!resultEntity.hasContent()) {
            StringBuilder stringBuilder = new StringBuilder()
                    .append("Tasks not found with status ").append(status);
            if (userId != null) {
                stringBuilder.append(" and userId ").append(userId);
            }
            throw new ResourceNotFoundException(
                    stringBuilder.toString()
            );
        }
        List<Task> resultDomain = resultEntity.getContent().stream().map(mapper::toDomain).collect(Collectors.toList());
        return new PagedResult<Task>(
                resultEntity.getNumber(),
                resultEntity.getSize(),
                resultEntity.getTotalElements(),
                resultEntity.getTotalPages(),
                resultDomain
        );
    }

}
