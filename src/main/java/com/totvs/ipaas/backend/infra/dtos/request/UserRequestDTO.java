package com.totvs.ipaas.backend.infra.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "Name cannot be blank")
        @Size(max = 75, message = "Name can have a maximum of 75 characters")
        String name,
        @Email(message = "Email is invalid")
        @NotBlank(message = "Email cannot be blank")
        @Size(max = 150, message = "Email can have a maximum of 150 characters")
        String email
) {
}
