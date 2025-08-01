package com.totvs.ipaas.backend.infra.dtos.response;

public record UserResponseDTO(
        String id,
        String name,
        String email
) {
}
