package com.totvs.ipaas.backend.infra.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SubTaskRequestDTO(
        @NotBlank(message = "Title cannot be null")
        @Size(max = 75, message = "Title can have a maximum of 75 characters")
        String title,
        @Size(max = 255, message = "Description can contain a maximum of 255 characters")
        String description
) {
}
