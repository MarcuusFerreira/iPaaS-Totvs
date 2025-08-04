package com.totvs.ipaas.backend.application.command.task;

import java.util.UUID;

public record ListTaskCommand(
        int page,
        int pageSize,
        String status,
        UUID userId
) {
}
