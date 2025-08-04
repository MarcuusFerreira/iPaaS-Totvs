package com.totvs.ipaas.backend.application.command;

import java.util.UUID;

public record ListTaskCommand(
        int page,
        int pageSize,
        String status,
        UUID userId
) {
}
