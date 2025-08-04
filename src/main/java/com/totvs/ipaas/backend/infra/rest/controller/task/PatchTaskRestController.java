package com.totvs.ipaas.backend.infra.rest.controller.task;

import com.totvs.ipaas.backend.application.usecases.interfaces.task.UpdateTaskStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "tasks")
public class PatchTaskRestController {

    private final UpdateTaskStatus updateTaskStatus;

    public PatchTaskRestController(UpdateTaskStatus updateTaskStatus) {
        this.updateTaskStatus = updateTaskStatus;
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> patchTaskStatus(@PathVariable("id") UUID id) {
        updateTaskStatus.execute(id);
        return ResponseEntity.noContent().build();
    }
}
