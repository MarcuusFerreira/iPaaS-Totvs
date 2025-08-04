package com.totvs.ipaas.backend.infra.rest.controller.task;

import com.totvs.ipaas.backend.application.command.task.CreateTaskCommand;
import com.totvs.ipaas.backend.application.usecases.interfaces.task.SaveTask;
import com.totvs.ipaas.backend.domain.models.task.Task;
import com.totvs.ipaas.backend.infra.dtos.request.TaskRequestDTO;
import com.totvs.ipaas.backend.infra.dtos.response.error.ApiErrorDTO;
import com.totvs.ipaas.backend.infra.dtos.response.subtask.SubTaskResponseDTO;
import com.totvs.ipaas.backend.infra.dtos.response.task.TaskResponseDTO;
import com.totvs.ipaas.backend.infra.mappers.TaskMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Tasks", description = "Operations related to the tasks resource")
public class PostTaskRestController {

    private final SaveTask saveTask;
    private final TaskMapper mapper;

    public PostTaskRestController(SaveTask saveTask, TaskMapper mapper) {
        this.saveTask = saveTask;
        this.mapper = mapper;
    }

    @Operation(summary = "Save the tasks", description = "Returns the saved task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Task created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation =  TaskResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error in the data sent by the client",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation =  ApiErrorDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "415",
                    description = "Unsupported media type",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation =  ApiErrorDTO.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<TaskResponseDTO> postTask(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = TaskRequestDTO.class)))
            @Valid @RequestBody TaskRequestDTO taskRequestDTO
    ) {
        CreateTaskCommand command = new CreateTaskCommand(taskRequestDTO.title(), taskRequestDTO.description(), taskRequestDTO.userId());
        Task task = saveTask.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(task));
    }
}
