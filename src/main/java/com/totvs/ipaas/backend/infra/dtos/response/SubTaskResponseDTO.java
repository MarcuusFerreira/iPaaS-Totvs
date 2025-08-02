package com.totvs.ipaas.backend.infra.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SubTaskResponseDTO(
        String id,
        String title,
        String description,
        String status,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ssXXX", timezone = "America/Sao_Paulo")
        OffsetDateTime creationDate,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ssXXX", timezone = "America/Sao_Paulo")
        OffsetDateTime completedDate,
        String taskId
) {
}
