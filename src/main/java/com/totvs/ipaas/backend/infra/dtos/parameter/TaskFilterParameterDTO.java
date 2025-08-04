package com.totvs.ipaas.backend.infra.dtos.parameter;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TaskFilterParameterDTO(
        @NotNull
        StatusTaskDTO status,
        UUID userId
) {
}
