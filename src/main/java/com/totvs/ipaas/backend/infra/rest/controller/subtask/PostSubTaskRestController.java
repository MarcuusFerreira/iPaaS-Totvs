package com.totvs.ipaas.backend.infra.rest.controller.subtask;

import com.totvs.ipaas.backend.application.command.subtask.CreateSubTaskCommand;
import com.totvs.ipaas.backend.application.usecases.interfaces.subtask.SaveSubTask;
import com.totvs.ipaas.backend.domain.models.subtask.SubTask;
import com.totvs.ipaas.backend.infra.dtos.request.SubTaskRequestDTO;
import com.totvs.ipaas.backend.infra.dtos.response.subtask.SubTaskResponseDTO;
import com.totvs.ipaas.backend.infra.mappers.SubTaskMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "tasks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PostSubTaskRestController {

    private final SaveSubTask saveSubTask;
    private final SubTaskMapper mapper;

    public PostSubTaskRestController(SaveSubTask saveSubTask, SubTaskMapper mapper) {
        this.saveSubTask = saveSubTask;
        this.mapper = mapper;
    }

    @PostMapping("{taskId}/subtasks")
    public ResponseEntity<SubTaskResponseDTO> postSubTask(
            @PathVariable("taskId") UUID taskId,
            @Valid @RequestBody SubTaskRequestDTO subTaskRequestDTO
    ) {
        CreateSubTaskCommand command = new CreateSubTaskCommand(subTaskRequestDTO.title(), subTaskRequestDTO.description(), taskId);
        SubTask subTask = saveSubTask.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(subTask));
    }
}
