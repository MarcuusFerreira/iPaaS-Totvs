package com.totvs.ipaas.backend.application.usecases.interfaces.subtask;

import java.util.UUID;

public interface UpdateSubTaskStatus {

    void execute(UUID id);

}
