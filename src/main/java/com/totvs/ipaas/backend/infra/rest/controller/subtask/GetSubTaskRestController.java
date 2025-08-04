package com.totvs.ipaas.backend.infra.rest.controller.subtask;

import com.totvs.ipaas.backend.application.command.subtask.ListSubTaskCommand;
import com.totvs.ipaas.backend.application.pagination.PagedResult;
import com.totvs.ipaas.backend.application.usecases.interfaces.subtask.ListSubTask;
import com.totvs.ipaas.backend.domain.models.subtask.SubTask;
import com.totvs.ipaas.backend.infra.dtos.parameter.PageParameterDTO;
import com.totvs.ipaas.backend.infra.dtos.parameter.StatusSubTaskDTO;
import com.totvs.ipaas.backend.infra.dtos.response.PaginationResponseDTO;
import com.totvs.ipaas.backend.infra.dtos.response.error.ApiErrorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/tasks")
@Tag(name = "SubTasks", description = "Operations related to the subtasks resource")
public class GetSubTaskRestController {

    private final ListSubTask listSubTask;

    public GetSubTaskRestController(ListSubTask listSubTask) {
        this.listSubTask = listSubTask;
    }

    @Operation(summary = "Find Sub Tasks by task id and filters", description = "Returns the subtasks that match the task id and filters")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Subtasks foud",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Subtasks not foud by task id and filter",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiErrorDTO.class)
                    )
            )
    })
    @GetMapping("{taskId}/subtasks")
    public ResponseEntity<PaginationResponseDTO> getSubTasks(
            @Parameter(description = "Pagination parameters (page, pageSize)")
            @Valid @ModelAttribute PageParameterDTO page,
            @Parameter(description = "The task id")
            @PathVariable("taskId") UUID taskId,
            @Parameter(description = "Optional filter, filter by status\n")
            @RequestParam(required = false) StatusSubTaskDTO status
    ) {
        String statusStr = status == null ? null : status.getValue();
        ListSubTaskCommand command = new ListSubTaskCommand(taskId, statusStr, page.page(), page.pageSize());
        PagedResult<SubTask> resultDomain = listSubTask.execute(command);
        PaginationResponseDTO response = PaginationResponseDTO.fromPagedResult(resultDomain);
        return ResponseEntity.ok().body(response);
    }

}
