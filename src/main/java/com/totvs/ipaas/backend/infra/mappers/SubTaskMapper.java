package com.totvs.ipaas.backend.infra.mappers;

import com.totvs.ipaas.backend.domain.models.SubTask;
import com.totvs.ipaas.backend.infra.dtos.response.SubTaskResponseDTO;
import com.totvs.ipaas.backend.infra.persistence.entities.SubTaskEntity;

public class SubTaskMapper {

    public SubTaskEntity toEntity(SubTask subTask) {
        return new SubTaskEntity(
                subTask.getId(),
                subTask.getTitle(),
                subTask.getDescription(),
                subTask.getStatus().getValue(),
                subTask.getCreationDate(),
                subTask.getCompletedDate(),
                subTask.getTaskId()
        );
    }

    public SubTask toDomain(SubTaskEntity subTaskEntity) {
        return new SubTask(
                subTaskEntity.getId(),
                subTaskEntity.getTitle(),
                subTaskEntity.getDescription(),
                subTaskEntity.getStatus().getValue(),
                subTaskEntity.getCreationDate(),
                subTaskEntity.getCompletedDate(),
                subTaskEntity.getTaskEntity().getId()
        );
    }

    public SubTaskResponseDTO toResponse(SubTask subTask) {
        return new SubTaskResponseDTO(
                subTask.getId().toString(),
                subTask.getTitle(),
                subTask.getDescription(),
                subTask.getStatus().getValue(),
                subTask.getCreationDate(),
                subTask.getCompletedDate(),
                subTask.getTaskId().toString()
        );
    }
}
