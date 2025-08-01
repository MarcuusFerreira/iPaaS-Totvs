package com.totvs.ipaas.backend.infra.rest.controller.task;

import com.totvs.ipaas.backend.application.command.CreateTaskCommand;
import com.totvs.ipaas.backend.application.usecases.interfaces.SaveTask;
import com.totvs.ipaas.backend.domain.models.Task;
import com.totvs.ipaas.backend.infra.dtos.request.TaskRequestDTO;
import com.totvs.ipaas.backend.infra.dtos.response.TaskResponseDTO;
import com.totvs.ipaas.backend.infra.mappers.TaskMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tasks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PostTaskRestController {

    private final SaveTask saveTask;
    private final TaskMapper mapper;

    public PostTaskRestController(SaveTask saveTask, TaskMapper mapper) {
        this.saveTask = saveTask;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> postTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        CreateTaskCommand command = new CreateTaskCommand(taskRequestDTO.title(), taskRequestDTO.description(), taskRequestDTO.userId());
        Task task = saveTask.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(task));
    }
}
