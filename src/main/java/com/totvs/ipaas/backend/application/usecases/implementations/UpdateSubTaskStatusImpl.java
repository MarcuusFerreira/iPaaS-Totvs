package com.totvs.ipaas.backend.application.usecases.implementations;

import com.totvs.ipaas.backend.application.gateways.SubTaskRepositoryInterface;
import com.totvs.ipaas.backend.application.usecases.interfaces.UpdateSubTaskStatus;
import com.totvs.ipaas.backend.domain.exception.ResourceNotFoundException;
import com.totvs.ipaas.backend.domain.models.SubTask;

import java.util.UUID;

public class UpdateSubTaskStatusImpl implements UpdateSubTaskStatus {

    private final SubTaskRepositoryInterface subTaskRepository;


    public UpdateSubTaskStatusImpl(SubTaskRepositoryInterface subTaskRepository) {
        this.subTaskRepository = subTaskRepository;
    }

    @Override
    public void execute(UUID id) {
        SubTask subTask = subTaskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("SubTask with id %s not found", id)));
        subTask.setStatus(subTask.getStatus().getNextStatus());
        subTaskRepository.save(subTask);
    }

}
