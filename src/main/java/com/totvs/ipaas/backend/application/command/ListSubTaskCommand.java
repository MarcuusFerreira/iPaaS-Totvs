package com.totvs.ipaas.backend.application.command;

import java.util.UUID;

public record ListSubTaskCommand(
        UUID taskId,
        String status,
        int page,
        int size
) {
}
