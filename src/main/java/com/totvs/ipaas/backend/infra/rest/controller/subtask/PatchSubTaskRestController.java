package com.totvs.ipaas.backend.infra.rest.controller.subtask;

import com.totvs.ipaas.backend.application.usecases.interfaces.UpdateSubTaskStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "subtasks")
public class PatchSubTaskRestController {

    private final UpdateSubTaskStatus updateSubTaskStatus;

    public PatchSubTaskRestController(UpdateSubTaskStatus updateSubTaskStatus) {
        this.updateSubTaskStatus = updateSubTaskStatus;
    }

    @PatchMapping("{id}/status")
    public ResponseEntity<?> patchSubTaskStatus(@PathVariable("id") UUID id) {
        updateSubTaskStatus.execute(id);
        return ResponseEntity.noContent().build();
    }

}
