package com.totvs.ipaas.backend.application.usecases.interfaces.task;

import java.util.UUID;

public interface UpdateTaskStatus {

    void execute(UUID id);
}
