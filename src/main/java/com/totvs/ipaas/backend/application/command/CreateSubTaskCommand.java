package com.totvs.ipaas.backend.application.command;

import java.util.UUID;

public record CreateSubTaskCommand(
        String title,
        String description,
        UUID taskId
) {
}
