package com.totvs.ipaas.backend.infra.rest.controller.task;

import com.totvs.ipaas.backend.application.command.ListTaskCommand;
import com.totvs.ipaas.backend.application.pagination.PagedResult;
import com.totvs.ipaas.backend.application.usecases.interfaces.ListTasks;
import com.totvs.ipaas.backend.domain.models.Task;
import com.totvs.ipaas.backend.infra.dtos.parameter.PageParameterDTO;
import com.totvs.ipaas.backend.infra.dtos.parameter.TaskFilterParameterDTO;
import com.totvs.ipaas.backend.infra.dtos.response.PaginationResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class GetTaskRestController {

    private final ListTasks listTasks;

    public GetTaskRestController(ListTasks listTasks) {
        this.listTasks = listTasks;
    }

    @GetMapping
    public ResponseEntity<PaginationResponseDTO> getTasks(
            @Valid @ModelAttribute PageParameterDTO page,
            @Valid @ModelAttribute TaskFilterParameterDTO taskFilter
            ) {
        ListTaskCommand command = new ListTaskCommand(page.page(), page.pageSize(), taskFilter.status().toString(), taskFilter.userId());
        PagedResult<Task> result = listTasks.execute(command);
        PaginationResponseDTO response = PaginationResponseDTO.fromPagedResult(result);
        return ResponseEntity.ok(response);
    }
}
