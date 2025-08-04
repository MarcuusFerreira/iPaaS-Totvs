package com.totvs.ipaas.backend.infra.dtos.response.user;

public record UserResponseDTO(
        String id,
        String name,
        String email
) {
}
