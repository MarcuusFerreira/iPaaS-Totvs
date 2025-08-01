package com.totvs.ipaas.backend.infra.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TaskRequestDTO(
        @NotBlank
        String title,
        String description,
        @NotNull
        UUID userId
) {
}
