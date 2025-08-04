package com.totvs.ipaas.backend.infra.rest.controller.subtask;

import com.totvs.ipaas.backend.application.command.subtask.ListSubTaskCommand;
import com.totvs.ipaas.backend.application.pagination.PagedResult;
import com.totvs.ipaas.backend.application.usecases.interfaces.subtask.ListSubTask;
import com.totvs.ipaas.backend.domain.models.subtask.SubTask;
import com.totvs.ipaas.backend.infra.dtos.parameter.PageParameterDTO;
import com.totvs.ipaas.backend.infra.dtos.parameter.StatusSubTaskDTO;
import com.totvs.ipaas.backend.infra.dtos.response.PaginationResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/tasks")
public class GetSubTaskRestController {

    private final ListSubTask listSubTask;

    public GetSubTaskRestController(ListSubTask listSubTask) {
        this.listSubTask = listSubTask;
    }

    @GetMapping("{taskId}/subtasks")
    public ResponseEntity<PaginationResponseDTO> getSubTasks(
            @Valid @ModelAttribute PageParameterDTO page,
            @PathVariable("taskId") UUID taskId,
            @RequestParam(required = false) StatusSubTaskDTO status
    ) {
        String statusStr = status == null ? null : status.getValue();
        ListSubTaskCommand command = new ListSubTaskCommand(taskId, statusStr, page.page(), page.pageSize());
        PagedResult<SubTask> resultDomain = listSubTask.execute(command);
        PaginationResponseDTO response = PaginationResponseDTO.fromPagedResult(resultDomain);
        return ResponseEntity.ok().body(response);
    }

}
