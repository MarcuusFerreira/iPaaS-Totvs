package com.totvs.ipaas.backend.infra.rest.controller.task;

import com.totvs.ipaas.backend.application.command.task.ListTaskCommand;
import com.totvs.ipaas.backend.application.pagination.PagedResult;
import com.totvs.ipaas.backend.application.usecases.interfaces.task.ListTasks;
import com.totvs.ipaas.backend.domain.models.task.Task;
import com.totvs.ipaas.backend.infra.dtos.parameter.PageParameterDTO;
import com.totvs.ipaas.backend.infra.dtos.parameter.TaskFilterParameterDTO;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tasks", description = "Operations related to the tasks resource")
public class GetTaskRestController {

    private final ListTasks listTasks;

    public GetTaskRestController(ListTasks listTasks) {
        this.listTasks = listTasks;
    }

    @Operation(summary = "Find tasks by status and filters", description = "Returns the tasks that match the status and filters")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The parameters sent have problems",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiErrorDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tasks not found by task id and filter",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiErrorDTO.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<PaginationResponseDTO> getTasks(
            @Parameter(description = "Pagination parameters (page, pageSize)")
            @Valid @ModelAttribute PageParameterDTO page,
            @Parameter(description = "Task query filters")
            @Valid @ModelAttribute TaskFilterParameterDTO taskFilter
            ) {
        ListTaskCommand command = new ListTaskCommand(page.page(), page.pageSize(), taskFilter.status().toString(), taskFilter.userId());
        PagedResult<Task> result = listTasks.execute(command);
        PaginationResponseDTO response = PaginationResponseDTO.fromPagedResult(result);
        return ResponseEntity.ok(response);
    }
}
