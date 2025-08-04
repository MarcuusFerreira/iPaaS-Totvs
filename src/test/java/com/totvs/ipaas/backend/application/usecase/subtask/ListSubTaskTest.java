package com.totvs.ipaas.backend.application.usecase.subtask;

import com.totvs.ipaas.backend.application.command.subtask.ListSubTaskCommand;
import com.totvs.ipaas.backend.application.gateways.SubTaskRepositoryInterface;
import com.totvs.ipaas.backend.application.pagination.PagedResult;
import com.totvs.ipaas.backend.application.usecases.implementations.subtask.ListSubTaskImpl;
import com.totvs.ipaas.backend.application.usecases.interfaces.subtask.ListSubTask;
import com.totvs.ipaas.backend.domain.exception.ResourceNotFoundException;
import com.totvs.ipaas.backend.domain.models.subtask.StatusSubTask;
import com.totvs.ipaas.backend.domain.models.subtask.SubTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListSubTaskTest {

    @Mock
    private SubTaskRepositoryInterface subTaskRepository;

    private ListSubTask listSubTask;

    @BeforeEach
    public void setup() {
        listSubTask = new ListSubTaskImpl(subTaskRepository);
    }

    @Test
    @DisplayName("Should return data")
    public void test01() {
        UUID taskId = UUID.randomUUID();
        ListSubTaskCommand command = new ListSubTaskCommand(taskId, "PENDING", 0, 10);

        List<SubTask> subTasks = List.of(
                new SubTask("SubTask 1", "Description 1", taskId),
                new SubTask("SubTask 2", "Description 1", taskId)
        );

        PagedResult<SubTask> expectedPagedResult = new PagedResult<>(1, 10, 2L, 1, subTasks);

        when(subTaskRepository.findByTaskIdAndStatus(taskId, StatusSubTask.PENDING, 0, 10))
                .thenReturn(expectedPagedResult);

        PagedResult<SubTask> result = listSubTask.execute(command);

        assertNotNull(result);
        assertEquals(result.totalElements(), 2);
        assertEquals(result.data().get(0).getTitle(), "SubTask 1");
    }

    @Test
    @DisplayName("Should throw ResourceNotFound")
    public void test02() {
        UUID taskId = UUID.randomUUID();
        ListSubTaskCommand command = new ListSubTaskCommand(taskId, "PENDING", 0, 10);

        doThrow(new ResourceNotFoundException("Resource not found")).when(subTaskRepository).findByTaskIdAndStatus(taskId, StatusSubTask.PENDING, 0, 10);
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            listSubTask.execute(command);
        });
        assertEquals("Resource not found", exception.getMessage());
    }

}
