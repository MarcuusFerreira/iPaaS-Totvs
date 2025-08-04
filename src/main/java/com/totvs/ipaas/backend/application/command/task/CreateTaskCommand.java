package com.totvs.ipaas.backend.application.command.task;

import java.util.UUID;

public record CreateTaskCommand(
        String title,
        String description,
        UUID userId
) {
}
