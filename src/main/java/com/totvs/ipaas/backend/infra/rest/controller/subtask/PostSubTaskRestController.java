package com.totvs.ipaas.backend.infra.rest.controller.subtask;

import com.totvs.ipaas.backend.application.command.subtask.CreateSubTaskCommand;
import com.totvs.ipaas.backend.application.usecases.interfaces.subtask.SaveSubTask;
import com.totvs.ipaas.backend.domain.models.subtask.SubTask;
import com.totvs.ipaas.backend.infra.dtos.request.SubTaskRequestDTO;
import com.totvs.ipaas.backend.infra.dtos.response.error.ApiErrorDTO;
import com.totvs.ipaas.backend.infra.dtos.response.subtask.SubTaskResponseDTO;
import com.totvs.ipaas.backend.infra.mappers.SubTaskMapper;
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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "tasks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "SubTasks", description = "Operations related to the subtasks resource")
public class PostSubTaskRestController {

    private final SaveSubTask saveSubTask;
    private final SubTaskMapper mapper;

    public PostSubTaskRestController(SaveSubTask saveSubTask, SubTaskMapper mapper) {
        this.saveSubTask = saveSubTask;
        this.mapper = mapper;
    }

    @Operation(summary = "Save the Subtasks", description = "Returns the saved subtask")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Subtasks created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation =  SubTaskResponseDTO.class)
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
    @PostMapping("{taskId}/subtasks")
    public ResponseEntity<SubTaskResponseDTO> postSubTask(
            @PathVariable("taskId") UUID taskId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content =  @Content(schema = @Schema(implementation = SubTaskRequestDTO.class)))
            @Valid @RequestBody SubTaskRequestDTO subTaskRequestDTO
    ) {
        CreateSubTaskCommand command = new CreateSubTaskCommand(subTaskRequestDTO.title(), subTaskRequestDTO.description(), taskId);
        SubTask subTask = saveSubTask.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(subTask));
    }
}
