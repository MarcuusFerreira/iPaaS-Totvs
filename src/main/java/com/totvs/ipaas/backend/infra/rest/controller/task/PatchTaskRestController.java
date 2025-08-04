package com.totvs.ipaas.backend.infra.rest.controller.task;

import com.totvs.ipaas.backend.application.usecases.interfaces.task.UpdateTaskStatus;
import com.totvs.ipaas.backend.infra.dtos.response.error.ApiErrorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "tasks")
@Tag(name = "Tasks", description = "Operations related to the tasks resource")
public class PatchTaskRestController {

    private final UpdateTaskStatus updateTaskStatus;

    public PatchTaskRestController(UpdateTaskStatus updateTaskStatus) {
        this.updateTaskStatus = updateTaskStatus;
    }

    @Operation(summary = "Updates the status of the task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Updates the status of the task and does not return data"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found by task id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiErrorDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The status update violated an application rule",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiErrorDTO.class)
                    )
            )
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> patchTaskStatus(@PathVariable("id") UUID id) {
        updateTaskStatus.execute(id);
        return ResponseEntity.noContent().build();
    }
}
