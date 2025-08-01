package com.totvs.ipaas.backend.infra.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
        @NotBlank(message = "Name cannot be blank")
        String name,
        @Email(message = "Email is invalid")
        @NotBlank(message = "Email cannot be blank")
        String email
) {
}
