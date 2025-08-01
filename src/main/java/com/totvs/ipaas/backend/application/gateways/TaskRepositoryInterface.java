package com.totvs.ipaas.backend.application.gateways;

import com.totvs.ipaas.backend.domain.models.Task;

public interface TaskRepositoryInterface {

    Task save(Task task);

}
