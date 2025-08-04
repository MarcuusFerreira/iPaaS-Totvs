package com.totvs.ipaas.backend.infra.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record TaskRequestDTO(
        @NotBlank(message = "Title cannot be null")
        @Size(max = 75, message = "Title can have a maximum of 75 characters")
        String title,
        @Size(max = 255, message = "Description can contain a maximum of 255 characters")
        String description,
        @NotNull(message = "User id cannot be null")
        UUID userId
) {
}
