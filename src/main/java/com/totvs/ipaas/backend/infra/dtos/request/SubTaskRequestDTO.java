package com.totvs.ipaas.backend.infra.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record SubTaskRequestDTO(
        @NotBlank
        String title,
        String description
) {
}
