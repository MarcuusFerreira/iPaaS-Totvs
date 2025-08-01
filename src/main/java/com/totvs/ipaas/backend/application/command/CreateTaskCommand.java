package com.totvs.ipaas.backend.application.command;

import java.util.UUID;

public record CreateTaskCommand(
        String title,
        String description,
        UUID userId
) {
}
