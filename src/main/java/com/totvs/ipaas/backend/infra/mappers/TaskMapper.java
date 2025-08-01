package com.totvs.ipaas.backend.infra.mappers;

import com.totvs.ipaas.backend.domain.models.Task;
import com.totvs.ipaas.backend.infra.dtos.response.TaskResponseDTO;
import com.totvs.ipaas.backend.infra.persistence.entities.TaskEntity;

public class TaskMapper {

    public Task toDomain(TaskEntity entity) {
        return new Task(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus().getValue(),
                entity.getCreationDate(),
                entity.getCompletedDate(),
                entity.getUserEntity().getId()
        );
    }

    public TaskEntity toEntity(Task task) {
        return new TaskEntity(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().getValue(),
                task.getCreationDate(),
                task.getCompletedDate(),
                task.getUserId()
        );
    }

    public TaskResponseDTO toResponse(Task task) {
        return new TaskResponseDTO(
                task.getId().toString(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().getValue(),
                task.getCreationDate(),
                task.getCompletedDate(),
                task.getUserId().toString()
        );
    }
}
